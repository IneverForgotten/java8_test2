package com.fanlm.statemachine;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

@AllArgsConstructor
@Slf4j
public class DefaultStateMachineListener<S, E> extends StateMachineListenerAdapter<S, E> {

    private final StateMachine<S, E> stateMachine;

    @Override
    public void eventNotAccepted(Message<E> event) {
        stateMachine.getExtendedState().getVariables().put("message", "当前状态不满足执行条件");
        stateMachine.setStateMachineError(new RuntimeException("Event not accepted"));
    }

    @Override
    public void transitionEnded(Transition<S, E> transition) {
        log.info("source {} to {}", transition.getSource().getId(), transition.getTarget().getId());
    }
}