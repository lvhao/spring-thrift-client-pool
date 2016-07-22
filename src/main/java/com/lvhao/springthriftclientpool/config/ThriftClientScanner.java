package com.lvhao.springthriftclientpool.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * scan 类路径下符合条件的类
 *
 * @author: lvhao
 * @since: 2016-7-21 19:19
 */
public class ThriftClientScanner extends ClassPathBeanDefinitionScanner {

    public ThriftClientScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public ThriftClientScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
        super.setBeanNameGenerator(beanNameGenerator);
    }

    @Override
    public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
        super.setScopeMetadataResolver(scopeMetadataResolver);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {

        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        beanDefinitionHolders.forEach(beanDefHolder -> {
            GenericBeanDefinition definition = (GenericBeanDefinition) beanDefHolder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            definition.getPropertyValues().add("thriftIface", beanClassName);

            // 面向服务
            // HelloService.Iface helloService = new HelloService.Client(tMultiplexedProtocol);
            String thriftImpl = StringUtils.replace(beanClassName,".Iface","Client");
            definition.getPropertyValues().add("thriftImpl", thriftImpl);
            String beanName = StringUtils.delete(beanDefHolder.getBeanName(),".Iface");

            // 将类名存入definition，供client proxy使用
            definition.getPropertyValues().add("beanName", beanName);

            // connection key
            ThriftConnectionConfig thriftConnectionConfig = new ThriftConnectionConfig();
            definition.getPropertyValues().add("thriftConnectionConfig",thriftConnectionConfig);
            definition.setBeanClass(ThriftClientFactoryBean.class);
        });
        return beanDefinitionHolders;
    }

    @Override
    protected void postProcessBeanDefinition(AbstractBeanDefinition beanDefinition, String beanName) {
        super.postProcessBeanDefinition(beanDefinition, beanName);
    }

    @Override
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        super.registerBeanDefinition(definitionHolder, registry);
    }
}
