package cn.linshenkx.halosyncserver.manager.impl;

import cn.linshenkx.halosyncserver.manager.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class StateRepositoryImplTest {

    private StateRepository stateRepository = new StateRepositoryImpl();

    @Test
    void getCommit() {
        log.info(stateRepository.getCommit());
    }

    @Test
    void setCommit() {
        stateRepository.setCommit("782041ae8a4ab319e64519e6b664ad4aec96d624");
    }
}