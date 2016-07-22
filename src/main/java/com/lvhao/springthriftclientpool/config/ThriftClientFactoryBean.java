package com.lvhao.springthriftclientpool.config;

import com.lvhao.springthriftclientpool.proxy.ThriftClientProxyHandler;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 创建thrift client 工厂bean
 *
 * @author: lvhao
 * @since: 2016-7-21 18:53
 */
public class ThriftClientFactoryBean<T> implements FactoryBean<T> {

    private Class<T> thriftIface;
    private Class<T> thriftImpl;
    private String beanName;
    private ThriftConnectionConfig thriftConnectionConfig;

    public ThriftClientFactoryBean() {
        super();
    }

    @Override
    public T getObject() throws Exception {
        ThriftClientProxyHandler handler = new ThriftClientProxyHandler(thriftConnectionConfig);
        return (T) Proxy.newProxyInstance(
                        Thread.currentThread().getContextClassLoader(),
                        new Class[] { thriftIface },
                        handler);
    }

    @Override
    public Class<?> getObjectType() {
        return this.thriftIface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
