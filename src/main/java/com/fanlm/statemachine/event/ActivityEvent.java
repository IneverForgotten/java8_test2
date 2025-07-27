package com.fanlm.statemachine.event;

public enum ActivityEvent {
    SAVE("保存草稿"),
    SUBMIT("提交审核"),
    PASS("审核通过"),
    REJECT("提交驳回"),
    ONLINE("上线"),
    OFFLINE("下线"),
    FINISH("结束");
    private final String desc;

    ActivityEvent(String desc){
        this.desc = desc;
    }
}
