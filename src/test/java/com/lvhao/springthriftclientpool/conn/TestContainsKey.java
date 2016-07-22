package com.lvhao.springthriftclientpool.conn;

import com.lvhao.springthriftclientpool.config.ThriftConnectionConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-21 12:54
 */
public class TestContainsKey {
    public static void main(String[] args) {
        ThriftConnectionConfig t1 = new ThriftConnectionConfig();
        t1.setHost("127.0.0.1");
        t1.setPort(1234);
        ThriftConnectionConfig t2 = new ThriftConnectionConfig();
        t2.setHost("127.0.0.2");
        t2.setPort(12345);

        ThriftConnectionConfig t3 = new ThriftConnectionConfig();
        t3.setHost("127.0.0.2");
        t3.setPort(12345);


        Map<ThriftConnectionConfig,String> map = new HashMap<>();
        map.put(t1,"t1");
        map.put(t2,"t2");
        map.put(t3,"t3");
        System.out.println(map.size());
    }
}
