package com.dcits.entity;

/**
 * @program: galaxy-test
 * @description:
 * @author: liu yan
 * @create: 2019-09-29 16:55
 */
public enum EnumSonic {
    //job运行信息任务状态参数
    JOB_RUN_FINISHED("已暂停",1),
    JOB_RUN_PAUSED("已完成",2),
    JOB_RUN_RUNNING("运行中",3),
    JOB_RUN_FAILURE("失败",4),
    //step运行状态信息
    STEP_NEW_BUILD("新建",5),
    STEP_FAIL("失败",6),
    STEP_FINISHED("执行完成",7),
    STEP_RUNNING("运行中",8),
    STEP_JUMP("跳过",9),
    STEP_SEND("派发中",10);
    // 成员变量
    private String state;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // 构造方法
    private EnumSonic(String state, int index) {
        this.state = state;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (EnumSonic c : EnumSonic.values()) {
            if (c.getIndex() == index) {
                return c.state;
            }
        }
        return null;
    }
    // get set 方法
}
