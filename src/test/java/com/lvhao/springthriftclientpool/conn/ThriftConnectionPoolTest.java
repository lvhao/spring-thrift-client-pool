package com.lvhao.springthriftclientpool.conn;

import com.lvhao.springthriftclientpool.config.ThriftConnectionConfig;
import com.lvhao.springthriftclientpool.config.ThriftConnectionPoolConfig;
import com.lvhao.springthriftclientpool.pool.ThriftConnectionFactory;
import com.lvhao.springthriftclientpool.pool.ThriftConnectionPool;
import com.lvhao.thrift.protocol.HelloService;
import com.lvhao.thrift.protocol.TReq;
import com.lvhao.thrift.protocol.TResp;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-20 17:37
 */
public class ThriftConnectionPoolTest {
    private static final Logger log = LoggerFactory.getLogger(ThriftConnectionPoolTest.class);

    private static final int PORT = 7777;

    // 模拟并发
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    // 连接Key配置
    private static ThriftConnectionConfig THRIFT_CONNECTION_KEY = new ThriftConnectionConfig();
    static {
        THRIFT_CONNECTION_KEY.setBasePackages(Arrays.asList("com.lvhao.springthriftclientpool"));
        THRIFT_CONNECTION_KEY.setHost("127.0.0.1");
        THRIFT_CONNECTION_KEY.setPort(PORT);
        THRIFT_CONNECTION_KEY.setTTransportType("org.apache.thrift.transport.TFramedTransport");
        THRIFT_CONNECTION_KEY.setTProtocolType("org.apache.thrift.protocol.TMultiplexedProtocol");
    }

    // 连接池配置
    private static ThriftConnectionPool CONNECT_POOL;
    static {
        ThriftConnectionFactory thriftConnectionFactory = new ThriftConnectionFactory();

        ThriftConnectionPoolConfig thriftConnectionPoolConfig = new ThriftConnectionPoolConfig();
        thriftConnectionPoolConfig.setBlockWhenExhausted(true);
        thriftConnectionPoolConfig.setFairness(false);

        // LIFO
        thriftConnectionPoolConfig.setLifo(true);

        // 链接活性检查
        thriftConnectionPoolConfig.setTestOnCreate(true);
        thriftConnectionPoolConfig.setTestOnBorrow(true);
        thriftConnectionPoolConfig.setTestOnReturn(false);
        thriftConnectionPoolConfig.setTestWhileIdle(true);

        // 允许最大活动对象数 key数量 * 池允许最大数 * 2
        thriftConnectionPoolConfig.setMaxTotal(200);

        // 每个key 设置
        thriftConnectionPoolConfig.setMinIdlePerKey(1);
        thriftConnectionPoolConfig.setMaxIdlePerKey(10);
        thriftConnectionPoolConfig.setMaxTotalPerKey(2);

        // wait 3s
        thriftConnectionPoolConfig.setMaxWaitMillis(3000);

        // evict 设置

        // 一次检查多少个链接
        thriftConnectionPoolConfig.setNumTestsPerEvictionRun(5);

        // 间隔80s 运行一次检测
        thriftConnectionPoolConfig.setTimeBetweenEvictionRunsMillis(80*1000);

        // 空闲存活1min
        thriftConnectionPoolConfig.setMinEvictableIdleTimeMillis(60*1000);
        THRIFT_CONNECTION_KEY.setThriftConnectionPoolConfig(thriftConnectionPoolConfig);

        CONNECT_POOL = new ThriftConnectionPool(
                thriftConnectionFactory,
                THRIFT_CONNECTION_KEY.getThriftConnectionPoolConfig()
        );
    }

    public static void poolTest(int seq){
        TResp tResp = new TResp();
        TFramedTransport tFramedTransport = null;
        try {
            tFramedTransport = CONNECT_POOL.borrowObject(THRIFT_CONNECTION_KEY);
            TCompactProtocol protocol = new TCompactProtocol(tFramedTransport);
            TMultiplexedProtocol tMultiplexedProtocol = new TMultiplexedProtocol(protocol,"HelloService");
            HelloService.Iface helloService = new HelloService.Client(tMultiplexedProtocol);

            TReq tReq = new TReq();
            tReq.setSeq(seq);
            tReq.setName("jack");

            tResp = helloService.sayHi(tReq);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CONNECT_POOL.returnObject(THRIFT_CONNECTION_KEY, tFramedTransport);
        }
        log.info(
            "count-> {} | tFramedTransport -> {} | tResp -> {}",
            CONNECT_POOL.getCreatedCount(),
            tFramedTransport.hashCode(),
            tResp
        );
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            EXECUTOR.submit(()->poolTest(1));
        }
        EXECUTOR.shutdown();
    }
}
