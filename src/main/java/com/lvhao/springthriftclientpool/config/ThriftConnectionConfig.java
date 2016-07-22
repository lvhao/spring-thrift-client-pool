package com.lvhao.springthriftclientpool.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Thrift Client 配置
 *
 * @author: lvhao
 * @since: 2016-7-19 17:21
 */
public class ThriftConnectionConfig extends BeanDefinitionRegistryPostProcessor {

    // bean ref
    private ThriftConnectionPoolConfig thriftConnectionPoolConfig;
    private String TTransportType;
    private String TProtocolType;
    private String host;
    private int port;
    private int timeout;
    private List<String> basePackages;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ThriftClientScanner scanner = new ThriftClientScanner(registry, this);
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    public ThriftConnectionPoolConfig getThriftConnectionPoolConfig() {
        return thriftConnectionPoolConfig;
    }

    public void setThriftConnectionPoolConfig(ThriftConnectionPoolConfig thriftConnectionPoolConfig) {
        this.thriftConnectionPoolConfig = thriftConnectionPoolConfig;
    }

    public String getTTransportType() {
        return TTransportType;
    }

    public void setTTransportType(String TTransportType) {
        this.TTransportType = TTransportType;
    }

    public String getTProtocolType() {
        return TProtocolType;
    }

    public void setTProtocolType(String TProtocolType) {
        this.TProtocolType = TProtocolType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThriftConnectionConfig that = (ThriftConnectionConfig) o;
        return getPort() == that.getPort() &&
                Objects.equals(getHost(), that.getHost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHost(), getPort());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThriftConnectionConfig{");
        sb.append("basePackages=").append(basePackages);
        sb.append(", host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append(", thriftConnectionPoolConfig=").append(thriftConnectionPoolConfig);
        sb.append(", timeout=").append(timeout);
        sb.append(", TProtocolType='").append(TProtocolType).append('\'');
        sb.append(", TTransportType='").append(TTransportType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
