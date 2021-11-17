package cn.linshenkx.halosyncserver.manager.impl;

import cn.linshenkx.halosyncserver.manager.GitManager;
import cn.linshenkx.halosyncserver.manager.HaloManager;
import cn.linshenkx.halosyncserver.manager.StateRepository;
import cn.linshenkx.halosyncserver.manager.SyncManager;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostMinimalDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;
import cn.linshenkx.halosyncserver.prop.HexoGitProp;
import cn.linshenkx.halosyncserver.utils.MarkdownUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SyncManagerImpl implements SyncManager {

    @Resource
    private HexoGitProp hexoGitProp;

    @Resource
    private GitManager gitManager;

    @Resource
    private HaloManager haloManager;

    @Resource
    private StateRepository stateRepository;

    /**
     * 1.获取起始commit
     * 2.根据init判断、执行初始化
     * 3.跟踪到最新的commit
     */
    @PostConstruct
    private void init() throws IOException, GitAPIException {
        String oldCommit = stateRepository.getCommit();
        if (StringUtils.isBlank(oldCommit)) {
            log.info("第一次运行，commit为空");
            String lastCommit = gitManager.getLastCommit().getName();
            if (hexoGitProp.isInit()) {
                log.info("开始同步历史文章");
                syncHistoryPost(lastCommit);
            }
            stateRepository.setCommit(lastCommit);
            log.info("当前完成同步commit为：{}", lastCommit);
        } else {
            log.info("非第一次运行，commit为:{}", oldCommit);
        }
        syncNewCommitPost();
    }

    @Override
    public void syncHistoryPost(String targetCommit) throws IOException {
        RevCommit commit = gitManager.buildCommit(targetCommit);
        Map<String, String> filePathMap = gitManager.getFilePathMap(commit, hexoGitProp.getSourceDir());
        List<BasePostSimpleDTO> postSimpleDTOList = haloManager.getAllBasePostSimpleDTO();
        Set<String> postNameSet = postSimpleDTOList.stream().map(BasePostMinimalDTO::getTitle).collect(Collectors.toSet());
        for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
            String fileContent = gitManager.getFileContent(commit, entry.getValue());
            String title = MarkdownUtils.getTitle(fileContent);
            if (!postNameSet.contains(MarkdownUtils.getTitle(fileContent))) {
                log.info("导入历史文章：{}", title);
                haloManager.importMarkdown(fileContent);
            }
        }
    }

    @Override
    public void syncNewCommitPost() throws IOException, GitAPIException {
        gitManager.fetch();
        String lastCommitStr = gitManager.getLastCommitStr();
        String oldCommitStr = stateRepository.getCommit();
        if (StringUtils.isBlank(oldCommitStr)) {
            return;
        }
        List<RevCommit> commitList = gitManager.getCommitListBetweenFromAndTo(oldCommitStr, lastCommitStr);
        for (RevCommit newCommit : commitList) {
            String newCommitStr = newCommit.getName();
            oldCommitStr = stateRepository.getCommit();
            if (oldCommitStr.equals(newCommitStr)) {
                return;
            }
            List<DiffEntry> diffEntryList = gitManager.listDiff(oldCommitStr, newCommitStr);
            RevCommit oldRevCommit = gitManager.buildCommit(oldCommitStr);
            log.info("发现新的commit：{}：\n{}", newCommit.getName(), newCommit.getFullMessage());
            log.info("Found: " + diffEntryList.size() + " differences");
            List<DiffEntry> validDiffEntryList = diffEntryList.stream().filter(diff -> {
                String oldPath = diff.getOldPath();
                String newPath = diff.getNewPath();
                switch (diff.getChangeType()) {
                    case ADD:
                        if (!newPath.startsWith(hexoGitProp.getSourceDir())) {
                            return false;
                        }
                        break;
                    case MODIFY:
                    case DELETE:
                        if (!oldPath.startsWith(hexoGitProp.getSourceDir())) {
                            return false;
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }).collect(Collectors.toList());
            log.info("Found: " + validDiffEntryList.size() + " valid differences");
            // 同时修改文件名和内容会被识别成 删除和新增，而非修改或重命名
            // 这个时候需要保证删除操作在新增前面
            validDiffEntryList.sort((o1, o2) -> {
                if (o1.getChangeType().equals(DiffEntry.ChangeType.DELETE) &&
                        (!o2.getChangeType().equals(DiffEntry.ChangeType.DELETE))
                ) {
                    return -1;
                }
                return 0;
            });
            for (DiffEntry diff : validDiffEntryList) {
                String oldPath = diff.getOldPath();
                String newPath = diff.getNewPath();
                log.info("Diff: " + diff.getChangeType() + ": " +
                        (diff.getOldPath().equals(diff.getNewPath()) ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath()));

                switch (diff.getChangeType()) {
                    case ADD:
                        String addedMd = gitManager.getFileContent(newCommit, newPath);
                        log.info("添加了文章：{}", MarkdownUtils.getTitle(addedMd));
                        haloManager.importMarkdown(addedMd);
                        break;
                    case MODIFY:
                        String oldMarkdown = gitManager.getFileContent(oldRevCommit, oldPath);
                        String oldTitle = MarkdownUtils.getTitle(oldMarkdown);
                        String modifiedMd = gitManager.getFileContent(newCommit, newPath);
                        log.info("修改了文章：{}", oldTitle);
                        haloManager.updateMarkdown(oldMarkdown, modifiedMd);
                        break;
                    case DELETE:
                        String deletedMd = gitManager.getFileContent(oldRevCommit, oldPath);
                        log.info("删除了文章：{}", MarkdownUtils.getTitle(deletedMd));
                        haloManager.deletePost(MarkdownUtils.getTitle(gitManager.getFileContent(oldRevCommit, oldPath)));
                        break;
                    case RENAME:
                    case COPY:
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + diff.getChangeType());
                }
            }
            stateRepository.setCommit(newCommitStr);
        }


    }
}
