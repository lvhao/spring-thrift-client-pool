package com.lvhao.springthriftclientpool.proxy;

import com.lvhao.springthriftclientpool.config.ThriftConnectionConfig;
import com.lvhao.thrift.protocol.HelloService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TFramedTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-21 17:54
 */
public class ThriftClientProxyHandler implements InvocationHandler {

    private ThriftConnectionConfig thriftConnectionConfig;

    public ThriftClientProxyHandler(ThriftConnectionConfig thriftConnectionConfig){
        this.thriftConnectionConfig = thriftConnectionConfig;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TFramedTransport tFramedTransport = thriftConnectionPool.borrowObject(thriftConnectionConfig);
        TCompactProtocol protocol = new TCompactProtocol(tFramedTransport);

        TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(protocol,"HelloService");
        HelloService.Iface serviceInstance = new HelloService.Client(tMultiplexedProtocol);
        // TODO
        Object result = method.invoke(serviceInstance,args);
        return result;
    }
}
