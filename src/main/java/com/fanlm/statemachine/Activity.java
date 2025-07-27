package com.fanlm.statemachine;

import com.fanlm.statemachine.state.ActivityState;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity
{
    private String machineId;
    private ActivityState state;
}
