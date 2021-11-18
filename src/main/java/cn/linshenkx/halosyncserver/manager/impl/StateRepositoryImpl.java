package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.io.FileUtil;
import cn.linshenkx.halosyncserver.manager.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class StateRepositoryImpl implements StateRepository {

    private String commitDbPath;

    @PostConstruct
    public void init() {
        String appHome = System.getenv("APP_HOME");
        String workspace = StringUtils.isNoneEmpty(appHome) ? appHome : System.getenv("PWD");
        String configDir = workspace + "/config";
        FileUtil.mkdir(configDir);
        commitDbPath = configDir + "/commit.db";
    }

    @Override
    public String getCommit() {
        try (DB db = DBMaker.fileDB(commitDbPath).make()) {
            return db.atomicString("commit").createOrOpen().get();
        }
    }

    @Override
    public void setCommit(String commit) {
        try (DB db = DBMaker.fileDB(commitDbPath).make()) {
            db.atomicString("commit").createOrOpen().set(commit);
        }
    }
}
