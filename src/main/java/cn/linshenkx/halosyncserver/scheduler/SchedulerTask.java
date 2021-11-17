package cn.linshenkx.halosyncserver.scheduler;

import cn.linshenkx.halosyncserver.manager.SyncManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@Configurable
@EnableScheduling
@EnableAsync
public class SchedulerTask {

    @Resource
    private SyncManager syncManager;


    @Async("taskExecutor")
    @Scheduled(fixedDelay = 60 * 1000)
    public void checkJobStatus() {
        try {
            syncManager.syncNewCommitPost();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
