package com.lvhao.springthriftclientpool.pool;

import com.lvhao.springthriftclientpool.config.ThriftConnectionConfig;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

/**
 * 连接管理
 *
 * @author: lvhao
 * @since: 2016-7-20 17:25
 */
public class ThriftConnectionFactory extends BaseKeyedPooledObjectFactory<ThriftConnectionConfig,TFramedTransport> {

    @Override
    public TFramedTransport create(ThriftConnectionConfig thriftConnectionConfig) throws Exception {
        String host = thriftConnectionConfig.getHost();
        int port = thriftConnectionConfig.getPort();
        TFramedTransport tFramedTransport = new TFramedTransport(new TSocket(host,port));
        tFramedTransport.open();
        return tFramedTransport;
    }

    @Override
    public PooledObject<TFramedTransport> wrap(TFramedTransport value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public void destroyObject(ThriftConnectionConfig key, PooledObject<TFramedTransport> p) throws Exception {
        TFramedTransport tFramedTransport = p.getObject();
        tFramedTransport.close();
    }

    @Override
    public boolean validateObject(ThriftConnectionConfig key, PooledObject<TFramedTransport> p) {
        TFramedTransport tFramedTransport = p.getObject();
        return tFramedTransport.isOpen();
    }
}
