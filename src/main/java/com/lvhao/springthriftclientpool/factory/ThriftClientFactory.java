package com.lvhao.springthriftclientpool.factory;

import com.lvhao.springthriftclientpool.model.ThriftServerModel;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.Consumer;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-12 16:14
 */
public class ThriftClientFactory implements InitializingBean,DisposableBean {

    private List<ThriftServerModel> thriftServerModels;

    private static final Consumer<List<ThriftServerModel>> loadThriftClientAction = (thriftServerModels) -> {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        thriftServerModels.stream().forEach(tsm -> {
            //加载Iface接口
            Class<?> objectClass = null;
            try {
                objectClass = classLoader.loadClass(tsm.getBasePackages().get(0)+ "$Iface");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //加载Client.Factory类
            Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>)classLoader.loadClass(service + "$Client$Factory");
            TServiceClientFactory<TServiceClient> clientFactory = fi.newInstance();
            ThriftClientPoolFactory clientPool = new ThriftClientPoolFactory(addressProvider, clientFactory,callback);
            GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
            poolConfig.maxActive = maxActive;
            poolConfig.minIdle = 0;
            poolConfig.minEvictableIdleTimeMillis = idleTime;
            poolConfig.timeBetweenEvictionRunsMillis = idleTime/2L;
            pool = new GenericObjectPool<TServiceClient>(clientPool,poolConfig);
            Object proxyClient = Proxy.newProxyInstance(
                    classLoader,
                    new Class[]{objectClass},
                    (proxy, method, args) -> {
                        //
                        TServiceClient client = pool.borrowObject();
                        try{
                            return method.invoke(client, args);
                        }catch(Exception e){
                            throw e;
                        }finally{
                            pool.returnObject(client);
                        }
                    }
            );
        });
    };

    private void loadThriftClient(List<ThriftServerModel> thriftServerModels){
        loadThriftClientAction.accept(thriftServerModels);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void destroy() throws Exception {

    }
}
