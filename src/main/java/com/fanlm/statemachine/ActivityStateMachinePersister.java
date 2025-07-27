package com.fanlm.statemachine;

import org.springframework.data.util.Pair;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.AbstractStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

public class ActivityStateMachinePersister<ActivityState, ActivityEvent, String> extends AbstractStateMachinePersister<ActivityState, ActivityEvent, String> {
    /**
     * Instantiates a new abstract state machine persister.
     *
     * @param stateMachinePersist the state machine persist
     */
    public ActivityStateMachinePersister(StateMachinePersist<ActivityState, ActivityEvent, String> stateMachinePersist) {
        super(stateMachinePersist);
    }
}