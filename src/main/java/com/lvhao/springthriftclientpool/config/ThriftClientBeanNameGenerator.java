package com.lvhao.springthriftclientpool.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.util.Random;

/**
 * BeanNameGenerator有两个实现版本，
 * {@link org.springframework.beans.factory.support.DefaultBeanNameGenerator}
 * {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}
 * 其中DefaultBeanNameGenerator是给资源文件加载bean时使用（BeanDefinitionReader中使用）
 * AnnotationBeanNameGenerator是为了处理注解生成bean name的情况。
 *
 * @author: lvhao
 * @since: 2016-7-18 18:22
 */
public class ThriftClientBeanNameGenerator extends AnnotationBeanNameGenerator {

    private Random randomIndex;

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        String defaultName = shortClassName + "$";
        while(registry.containsBeanDefinition(defaultName)){
            randomIndex = new Random();
            defaultName = shortClassName + randomIndex.nextInt();
        }
        return defaultName;
    }
}
