package cn.linshenkx.halosyncserver;

import cn.linshenkx.halosyncserver.manager.GitManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HaloSyncServerApplicationTests {

    @Resource
    private GitManager gitManager;

    @BeforeAll
    public static void init(){
        System.setProperty("proxyHost", "127.0.0.1");
        System.setProperty("proxyPort", "7890");
    }

    @Test
    void contextLoads() {
    }

//    @Test
//    public void WalkAllCommits() throws GitAPIException, IOException {
//        Repository repository = gitManager.getRepository();
//        // get a list of all known heads, tags, remotes, ...
//        Collection<Ref> allRefs = repository.getRefDatabase().getRefs(RefDatabase.ALL).values();
//
//        // a RevWalk allows to walk over commits based on some filtering that is defined
//        try (RevWalk revWalk = new RevWalk(repository)) {
//            for (Ref ref : allRefs) {
//                revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
//            }
//            System.out.println("Walking all commits starting with " + allRefs.size() + " refs: " + allRefs);
//            int count = 0;
//            for (RevCommit commit : revWalk) {
////                    System.out.println("Commit: " + commit);
//                System.out.println(new Date(new Long(commit.getCommitTime() * 1000)) + ":" + commit.getId());
//                System.out.println("    " + commit.getFullMessage());
//                count++;
//            }
//            System.out.println("Had " + count + " commits");
//        }
//    }


}
