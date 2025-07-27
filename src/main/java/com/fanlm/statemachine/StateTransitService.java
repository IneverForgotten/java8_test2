package com.fanlm.statemachine;


import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.state.ActivityState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StateTransitService {
    @Autowired
    private StateMachineService<ActivityState, ActivityEvent> stateMachineService;

    @Transactional
    public void transimit(String machineId, Message<ActivityEvent> message) {
        StateMachine<ActivityState, ActivityEvent> stateMachine = stateMachineService.acquireStateMachine(machineId);
        stateMachine.addStateListener(new DefaultStateMachineListener<>(stateMachine));
        stateMachine.sendEvent(message);
        if (stateMachine.hasStateMachineError()) {
            String errorMessage = stateMachine.getExtendedState().get("message", String.class);
            stateMachineService.releaseStateMachine(machineId);
            throw new RuntimeException(errorMessage);
        }
    }
}
