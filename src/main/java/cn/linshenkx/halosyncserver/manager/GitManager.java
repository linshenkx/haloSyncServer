package cn.linshenkx.halosyncserver.manager;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GitManager {

    void fetch() throws GitAPIException;

    RevCommit buildCommit(String commit) throws IOException;

    List<RevCommit> getCommitListBetweenFromAndTo(String fromCommit, String toCommit) throws IOException, GitAPIException;

    RevCommit getLastCommit() throws IOException;

    String getLastCommitStr() throws IOException;

    Map<String, String> getFilePathMap(RevCommit commit, String path) throws IOException;

    String getFileContent(RevCommit commit, String filePath) throws IOException;

    List<DiffEntry> listDiff(String oldCommit, String newCommit) throws GitAPIException, IOException;
}
