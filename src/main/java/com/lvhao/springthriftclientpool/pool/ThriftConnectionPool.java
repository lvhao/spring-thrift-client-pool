package com.lvhao.springthriftclientpool.pool;

import com.lvhao.springthriftclientpool.config.ThriftConnectionConfig;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.thrift.transport.TFramedTransport;

/**
 * 连接池
 *
 * @author: lvhao
 * @since: 2016-7-20 17:26
 */
public class ThriftConnectionPool extends GenericKeyedObjectPool<ThriftConnectionConfig,TFramedTransport> {
    
    public ThriftConnectionPool(KeyedPooledObjectFactory<ThriftConnectionConfig, TFramedTransport> factory, GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }
}
