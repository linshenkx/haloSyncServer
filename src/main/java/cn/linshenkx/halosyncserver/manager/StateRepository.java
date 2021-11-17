package cn.linshenkx.halosyncserver.manager;

public interface StateRepository {

    String getCommit();

    void setCommit(String commit);
}
