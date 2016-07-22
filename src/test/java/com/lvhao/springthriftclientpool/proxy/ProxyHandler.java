package com.lvhao.springthriftclientpool.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-18 17:22
 */
public class ProxyHandler implements InvocationHandler {

    private Object proxied;

    public ProxyHandler(Object obj){
        this.proxied = obj;
    }

    public void beforeHandle(){
        System.out.println("--before--");
    }

    public void afterHandle(){
        System.out.println("--after--");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeHandle();
        Object result = method.invoke(proxied,args);
        afterHandle();
        return result;
    }
}
