package com.fanlm.statemachine.state;

public enum ActivityState {
    INIT("初始化"),
    DRAFT("草稿"),
    WAIT_VERIFY("待审核"),
    PASSED("审核通过"),
    REJECTED("审核未通过"),
    WAIT_ONLIT("待上线"),
    ONLINED("已上线"),
    OFFLINING("下线中"),
    FINISHED("已结束");

    private final String desc;

    ActivityState(String desc) {
        this.desc = desc;
    }
}
