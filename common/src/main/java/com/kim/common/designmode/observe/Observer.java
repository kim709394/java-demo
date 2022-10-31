package com.kim.common.designmode.observe;

/**
 * @author huangjie
 * @description  观察者接口
 * @date 2022-10-31
 */
public interface Observer {

    /**
     * 监听方法
     * */
    void observe(String event);
}
