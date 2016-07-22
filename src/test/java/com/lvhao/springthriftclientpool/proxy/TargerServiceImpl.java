package com.lvhao.springthriftclientpool.proxy;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-18 17:48
 */
public class TargerServiceImpl implements TargetService {
    @Override
    public void sayHi(String yourName) {
        System.out.println("Hi," + yourName);
    }
}
