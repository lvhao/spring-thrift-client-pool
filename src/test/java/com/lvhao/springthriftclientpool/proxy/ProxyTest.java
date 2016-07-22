package com.lvhao.springthriftclientpool.proxy;

import java.lang.reflect.Proxy;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-18 15:26
 */
public class ProxyTest {
    public static void main(String[] args) {
        TargetService ts = new TargerServiceImpl();
        Object proxy = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{TargetService.class},
                new ProxyHandler(ts)
        );
        TargetService targetService = (TargetService) proxy;
        targetService.sayHi("test");
    }
}
