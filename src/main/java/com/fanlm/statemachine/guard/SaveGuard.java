package com.fanlm.statemachine.guard;

import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.state.ActivityState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SaveGuard implements Guard<ActivityState, ActivityEvent> {
    @Override
    public boolean evaluate(StateContext<ActivityState, ActivityEvent> context){
        log.info("[execute save guard]");
        return true;
    }
}
