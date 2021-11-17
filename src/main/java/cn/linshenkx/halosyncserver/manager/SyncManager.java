package cn.linshenkx.halosyncserver.manager;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface SyncManager {

    /**
     * 将halo同步到特定的commit
     * 策略：获取hexo和halo的所有文章，根据名称进行比对，将halo缺少的文章进行补全
     * 注意：对原来的文章不会进行删除！
     */
    void syncHistoryPost(String targetCommit) throws IOException;

    /**
     * 根据最近一次记录的commit和git上最新的一次commit，
     * 比较md文件差异，根据比较结果进行文章的增删改
     *
     * @throws IOException
     * @throws GitAPIException
     */
    void syncNewCommitPost() throws IOException, GitAPIException;

}
