package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Assert;
import cn.linshenkx.halosyncserver.httpclient.HaloHttpClient;
import cn.linshenkx.halosyncserver.manager.HaloManager;
import cn.linshenkx.halosyncserver.model.AuthToken;
import cn.linshenkx.halosyncserver.model.PageObject;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostDetailDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostMinimalDTO;
import cn.linshenkx.halosyncserver.model.dto.post.BasePostSimpleDTO;
import cn.linshenkx.halosyncserver.model.enums.PostStatus;
import cn.linshenkx.halosyncserver.model.params.LoginParam;
import cn.linshenkx.halosyncserver.model.params.PostParam;
import cn.linshenkx.halosyncserver.model.params.PostQuery;
import cn.linshenkx.halosyncserver.model.vo.PostDetailVO;
import cn.linshenkx.halosyncserver.prop.HaloGitProp;
import cn.linshenkx.halosyncserver.utils.ByteMultipartFile;
import cn.linshenkx.halosyncserver.utils.MarkdownUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HaloManagerImpl implements HaloManager {

    @Resource
    private HaloGitProp haloGitProp;
    @Resource
    private HaloHttpClient haloHttpClient;

    private String token;
    private Long tokenExpiredTime;


    @Override
    public String getLoginToken(boolean force) {
        if (force || StringUtils.isBlank(token) || tokenExpiredTime < System.currentTimeMillis()) {
            LoginParam loginParam = new LoginParam();
            loginParam.setUsername(haloGitProp.getUsername());
            loginParam.setPassword(haloGitProp.getPassword());
            AuthToken auth = haloHttpClient.auth(loginParam).getData();
            token = auth.getAccessToken();
            tokenExpiredTime = System.currentTimeMillis() + (auth.getExpiredIn() - 60) * 1000L;
            log.info("获取token成功，tokenExpiredTime为：{}", new DateTime(tokenExpiredTime));
        }
        return token;
    }

    @Override
    public List<BasePostSimpleDTO> getAllBasePostSimpleDTO() {
        PageObject<BasePostSimpleDTO> page = haloHttpClient.pageByStatus(PostStatus.PUBLISHED, Pageable.ofSize(Integer.MAX_VALUE), false).getData();
        return page.getContent();
    }

    @Override
    public BasePostDetailDTO importMarkdown(String fileContent) {
        return haloHttpClient.backupMarkdowns(new ByteMultipartFile(MarkdownUtils.getTitle(fileContent), fileContent.getBytes(StandardCharsets.UTF_8))).getData();
    }

    @Override
    public List<BasePostSimpleDTO> getPostListByTitle(String title) {
        PostQuery postQuery = new PostQuery();
        // keyword 查不了 slug
        postQuery.setKeyword(title);
        return haloHttpClient.pageBy(Pageable.ofSize(Integer.MAX_VALUE), postQuery, false).getData().getContent()
                .stream().filter(post -> post.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public void deletePost(String name) {
        haloHttpClient.deletePermanentlyInBatch(getPostListByTitle(name).stream().map(BasePostMinimalDTO::getId).collect(Collectors.toList()));
    }

    @Override
    public void updateMarkdown(String oldMarkdown, String newMarkdown) {
        String oldTitle = MarkdownUtils.getTitle(oldMarkdown);
        String newTitle = MarkdownUtils.getTitle(newMarkdown);
        Assert.notNull(oldTitle, "markdown 中title不能为空");
        Assert.notNull(newTitle, "markdown 中title不能为空");
        // halo会优先提取permalink（其次是title）作为slug唯一标识，所以需要将permalink和title都替换掉
        String oldPermalink = MarkdownUtils.getFrontValue(oldMarkdown, "permalink");
        List<BasePostSimpleDTO> postList = getPostListByTitle(oldTitle);
        if (postList.size() != 1) {
            throw new IllegalStateException("标题为：" + oldTitle + " 的文章数量为：" + postList.size() + ",无法进行updateMarkdown操作");
        }
        BasePostSimpleDTO oldPost = postList.get(0);
        String tmpTitle = "tmp_" + oldTitle + "_" + System.currentTimeMillis();
        String tmpMarkdown = MarkdownUtils.replaceFrontValue(newMarkdown, "title", tmpTitle);
        if (StringUtils.isNotBlank(oldPermalink)) {
            String tmpPermalink = "tmp_" + oldPermalink + "_" + System.currentTimeMillis();
            tmpMarkdown = MarkdownUtils.replaceFrontValue(tmpMarkdown, "permalink", tmpPermalink);
        }
        PostDetailVO tmpPost = haloHttpClient.getByPostId(importMarkdown(tmpMarkdown).getId()).getData();
        PostParam postParam = new PostParam();
        BeanUtil.copyProperties(tmpPost, postParam, CopyOptions.create().setIgnoreNullValue(true));
        //title可以修改
        postParam.setTitle(newTitle);
        //slug不能修改
        postParam.setSlug(oldPost.getSlug());
        haloHttpClient.updateBy(postParam, oldPost.getId(), true);
        haloHttpClient.deletePermanently(tmpPost.getId());
    }
}
