package cn.linshenkx.halosyncserver.manager.impl;

import cn.hutool.core.io.FileUtil;
import cn.linshenkx.halosyncserver.manager.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StateRepositoryImpl implements StateRepository {

    private static final String COMMIT_DB_PATH = "config/commit.db";

    static {
        FileUtil.mkdir("config");
    }

    @Override
    public String getCommit() {
        try (DB db = DBMaker.fileDB(COMMIT_DB_PATH).make()) {
            return db.atomicString("commit").createOrOpen().get();
        }
    }

    @Override
    public void setCommit(String commit) {
        try (DB db = DBMaker.fileDB(COMMIT_DB_PATH).make()) {
            db.atomicString("commit").createOrOpen().set(commit);
        }
    }
}
