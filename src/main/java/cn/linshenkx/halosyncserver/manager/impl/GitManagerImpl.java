package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.io.IoUtil;
import cn.linshenkx.halosyncserver.manager.GitManager;
import cn.linshenkx.halosyncserver.prop.HexoGitProp;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.sshd.JGitKeyCache;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.transport.sshd.SshdSessionFactoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FS;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Shinelon
 */
@Slf4j
@DependsOn({"hexoGitProp"})
@Service
public class GitManagerImpl implements GitManager {

    //    File sshDir = new File("D:\\Dev\\myProject\\haloSyncServer\\src\\main\\resources\\ssh");
    File sshDir = new File(FS.DETECTED.userHome(), ".ssh");
    SshdSessionFactory sshdSessionFactory = new SshdSessionFactoryBuilder()
            .setPreferredAuthentications("publickey,keyboard-interactive,password")
//                .setDefaultIdentities(new Function<File, List<Path>>() {
//                    @Override
//                    public List<Path> apply(File file) {
//                        return Lists.newArrayList(new File(file, "key").toPath());
//                    }
//                })
//            .setHomeDirectory(new File("D:\\Dev\\myProject\\haloSyncServer\\src\\main\\resources"))
            .setHomeDirectory(FS.DETECTED.userHome())
            .setSshDirectory(sshDir).build(new JGitKeyCache());
    @Resource
    private HexoGitProp hexoGitProp;
    private Git git;

//    private void cloneRepo(File repoFile) throws GitAPIException {
//        long startTime = System.currentTimeMillis();
//        System.setProperty("proxyHost", "127.0.0.1");
//        System.setProperty("proxyPort", "7890");
//        try (Git result = Git.cloneRepository()
//                .setURI(hexoGitProp.getUrl())
//                .setDirectory(repoFile)
//                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(hexoGitProp.getUsername(), hexoGitProp.getPassword()))
//                .setTimeout(60 * 10)
//                .call()) {
//            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
//            System.out.println("Having repository: " + result.getRepository().getDirectory());
//        }
//        System.out.println("连接耗时(s)：" + (System.currentTimeMillis() - startTime) / 1000);
//    }

    //    @PostConstruct
//    public void openRepositoryInFIle() throws IOException, GitAPIException {
//        String repoName = hexoGitProp.getUrl().substring(hexoGitProp.getUrl().lastIndexOf("/") + 1, hexoGitProp.getUrl().lastIndexOf("."));
//        // prepare a new folder for the cloned repository
//        File repoFile = new File(System.getProperty("java.io.tmpdir") + repoName);
//        System.out.println("repo path:" + repoFile.getAbsolutePath());
//        if (!repoFile.exists()) {
//            cloneRepo(repoFile);
//        }
//        git = Git.open(repoFile);
//        repo = git.getRepository();
//    }
    private Repository repo;

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }

    @PostConstruct
    public void init() throws GitAPIException {
//        SshdSessionFactory factory = new SshdSessionFactory(
//                new JGitKeyCache(), new DefaultProxyDataFactory());
//        try {
//            Runtime.getRuntime()
//                    .addShutdownHook(new Thread(sshdSessionFactory::close));
//        } catch (IllegalStateException e) {
//            // ignore - the VM is already shutting down
//        }
//        SshSessionFactory.setInstance(factory);
//        SshSessionFactory.setInstance(sshdSessionFactory);
        DfsRepositoryDescription repoDesc = new DfsRepositoryDescription();
        repo = new InMemoryRepository(repoDesc);
        git = new Git(repo);
        /**
         * 会把整个仓库拉下来，很费时间！
         */
        git.fetch()
                .setRemote(hexoGitProp.getUrl())
                .setRefSpecs(new RefSpec("+refs/heads/*:refs/heads/*"))
//                .setTransportConfigCallback(new TransportConfigCallback() {
//                    @Override
//                    public void configure(Transport transport) {
//                        SshTransport sshTransport = (SshTransport) transport;
//                        sshTransport.setSshSessionFactory(sshSessionFactory);
//                    }
//                })
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(hexoGitProp.getUsername(), hexoGitProp.getPassword()))
                .call();
    }

    @Override
    public void fetch() throws GitAPIException {
        git.fetch()
                .setRemote(hexoGitProp.getUrl())
                .setRefSpecs(new RefSpec("+refs/heads/*:refs/heads/*"))
//                .setTransportConfigCallback(new TransportConfigCallback() {
//                    @Override
//                    public void configure(Transport transport) {
//                        SshTransport sshTransport = (SshTransport) transport;
//                        sshTransport.setSshSessionFactory(sshSessionFactory);
//                    }
//                })
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(hexoGitProp.getUsername(), hexoGitProp.getPassword()))
                .call();
    }


    @Override
    public RevCommit buildCommit(String commit) throws IOException {
        try (RevWalk revWalk = new RevWalk(repo)) {
            return revWalk.parseCommit(ObjectId.fromString(commit));
        }
    }

    @Override
    public List<RevCommit> getCommitListBetweenFromAndTo(String fromCommit, String toCommit) throws IOException, GitAPIException {
        ArrayList<RevCommit> commitList = Lists.newArrayList(git.log().addRange(buildCommit(fromCommit), buildCommit(toCommit)).call());
        Collections.reverse(commitList);
        return commitList;
    }

    @Override
    public RevCommit getLastCommit() throws IOException {
        try (RevWalk revWalk = new RevWalk(repo)) {
            ObjectId lastCommitId = repo.resolve(hexoGitProp.getBranch());
            return revWalk.parseCommit(lastCommitId);
        }
    }

    @Override
    public String getLastCommitStr() throws IOException {
        return getLastCommit().getName();
    }

    @Override
    public Map<String, String> getFilePathMap(RevCommit commit, String path) throws IOException {
        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(path));
        Map<String, String> fileNamePathMap = Maps.newHashMap();
        while (treeWalk.next()) {
            fileNamePathMap.put(treeWalk.getNameString(), treeWalk.getPathString());
        }
        return fileNamePathMap;
    }

    @Override
    public String getFileContent(RevCommit commit, String filePath) throws IOException {
        RevTree tree = commit.getTree();
        TreeWalk treeWalk = new TreeWalk(repo);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(filePath));
        if (treeWalk.next()) {
            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = repo.open(objectId);
            try (InputStream in = loader.openStream()) {
                return IoUtil.read(in, StandardCharsets.UTF_8);
            }
        } else {
            throw new FileNotFoundException("filePath not found:" + filePath);
        }
    }

    @Override
    public List<DiffEntry> listDiff(String oldCommit, String newCommit) throws GitAPIException, IOException {

        return git.diff()
                .setOldTree(prepareTreeParser(repo, oldCommit))
                .setNewTree(prepareTreeParser(repo, newCommit))
                .call();
    }

}
