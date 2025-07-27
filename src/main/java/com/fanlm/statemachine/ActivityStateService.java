package com.fanlm.statemachine;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanlm.bean.StateMachineContext;
import com.fanlm.mapper.StateMachineContextMapper;
import com.fanlm.statemachine.state.ActivityState;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 持久化
 */
@Service
@RequiredArgsConstructor
public class ActivityStateService {

    private final StateMachineContextMapper stateMachineContextMapper;

    public Activity findById(String machineId) {
        StateMachineContext activityMachine = stateMachineContextMapper.selectOne(Wrappers.lambdaQuery(StateMachineContext.class)
                .eq(StateMachineContext::getMachineType, "activity_machine").eq(StateMachineContext::getMachineId, machineId));
        return Activity.builder().machineId(machineId).state(ActivityState.valueOf(activityMachine.getMachineState())).build();
    }

    public void save(Activity activity) {
        stateMachineContextMapper.insert(StateMachineContext.builder().machineType("activity_machine")
                .machineId(activity.getMachineId()).machineState(activity.getState().name()).build());
    }
}
