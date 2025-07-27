package com.fanlm.statemachine.action;

import com.fanlm.statemachine.event.ActivityEvent;
import com.fanlm.statemachine.state.ActivityState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PassAction implements Action<ActivityState, ActivityEvent> {
    @Override
    public void execute(StateContext<ActivityState, ActivityEvent> stateContext) {
        try {
            log.info("[execute PassAction]");
        } catch (Exception e) {
            stateContext.getExtendedState().getVariables().put("ERROR", e.getMessage());
        }
    }
}
