package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.date.DateTime;
import cn.linshenkx.halosyncserver.manager.GitManager;
import cn.linshenkx.halosyncserver.prop.HexoGitProp;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class GitManagerImplTest {

    @Resource
    private HexoGitProp hexoGitProp;
    @Resource
    private GitManager gitManager;

    @BeforeAll
    public static void init() {
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "7890");
    }

    @Test
    void getCommitList() throws IOException, GitAPIException {
        List<RevCommit> commitList = gitManager.getCommitListBetweenFromAndTo("a5e66135c99517c07c21f8a33283f7fe021c50e2", "782041ae8a4ab319e64519e6b664ad4aec96d624");
        for (RevCommit commit : commitList) {
            log.info("{}:{}:{}", new DateTime(commit.getCommitTime() * 1000L), commit.getId(), commit.getFullMessage());
        }
    }

    @Test
    void buildCommit() throws IOException {
        RevCommit revCommit = gitManager.buildCommit("981ac624496084f948da0cc866f9092c618cca86");
        Assertions.assertEquals("删除package-lock.json，删除live2d-widget-model-hibiki依赖\n", revCommit.getFullMessage());
    }

    @Test
    void getLastCommit() throws IOException {
        RevCommit revCommit = gitManager.getLastCommit();
        System.out.println(revCommit.getName());
        System.out.println(revCommit.getId().getName());
        System.out.println(new Date(new Long(revCommit.getCommitTime() * 1000)) + ":" + revCommit.getId());
        Assertions.assertEquals("update\n", revCommit.getFullMessage());
    }

    @Test
    void getLastCommitStr() throws IOException {
        System.out.println("getLastCommitStr:" + gitManager.getLastCommitStr());
    }

    @Test
    void getFilePathMap() throws IOException {
        RevCommit commit = gitManager.getLastCommit();
        Map<String, String> filePathMap = gitManager.getFilePathMap(commit, hexoGitProp.getSourceDir());
        System.out.println(JSON.toJSONString(filePathMap));
    }

    @Test
    void getFileContent() throws IOException {
        String content = gitManager.getFileContent(gitManager.buildCommit("ac7a0728b8ba19cf88b09eec46ecfaa6f6c67e86"), hexoGitProp.getSourceDir() + "/_posts/大数据/" + "大数据通用计算平台(支持flink、spark等)(1)系统调研及设计.md");
        System.out.println(content);
    }

    @Test
    void listDiff() throws GitAPIException, IOException {
        List<DiffEntry> diffEntryList = gitManager.listDiff("3ddec0b4694ecab6c9ab0561d7f21a10820f41a8", "981ac624496084f948da0cc866f9092c618cca86");
        for (DiffEntry diff : diffEntryList) {
            System.out.println("Diff: " + diff.getChangeType() + ": " +
                    (diff.getOldPath().equals(diff.getNewPath()) ? diff.getNewPath() : diff.getOldPath() + " -> " + diff.getNewPath()));
        }

    }
}