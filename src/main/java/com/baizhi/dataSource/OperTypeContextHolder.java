package com.baizhi.dataSource;

public class OperTypeContextHolder {
    //通过同一个线程共享变量来传递类型
    private static final ThreadLocal<OperType> OPER_TYPE_THREAD_LOCAL = new ThreadLocal<>();

    //设置操作类型
    public static void setOperType(OperType operType) {
        OPER_TYPE_THREAD_LOCAL.set(operType);
    }

    //获取操作类型
    public static OperType getOperType() {
        return OPER_TYPE_THREAD_LOCAL.get();
    }

    //清除数据
    public static void clearOperType() {
        OPER_TYPE_THREAD_LOCAL.remove();
    }
}
