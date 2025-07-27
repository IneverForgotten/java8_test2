package com.fanlm.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("state_machine_context")
public class StateMachineContext {
    private Long id;
    // 机器类型
    private String machineType;
    // 状态机id
    private String machineId;
    // 状态机数据
    private String machineData;
    //状态机状态
    private String machineState;
    // 状态机事件
    private String machineEvent;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
