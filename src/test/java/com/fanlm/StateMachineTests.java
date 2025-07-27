package com.fanlm;

import com.fanlm.statemachine.ActivityStateService;
import com.fanlm.statemachine.StateTransitService;
import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.state.ActivityState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
public class StateMachineTests {
    @Autowired
    private StateTransitService transmitService;

    @Autowired
    private ActivityStateService activityStateService;

    @Test
    public void test() {
        String machineId = "202501081";//业务主键ID
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.SAVE).build());
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.SUBMIT).build());
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.PASS).build());
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.ONLINE).build());
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.ONLINE).build());
        transmitService.transimit(machineId, MessageBuilder.withPayload(ActivityEvent.OFFLINE).build());
//        assert activityStateService.getStateById(machineId).equals(ActivityState.FINISHED);
    }
}
