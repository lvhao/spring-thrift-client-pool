package com.lvhao.springthriftclientpool.model;

import java.util.List;

/**
 * 功能简单描述
 *
 * @author: lvhao
 * @since: 2016-7-12 16:56
 */
public class ThriftServerModel {
    private String domain;
    private String port;
    private List<String> basePackages;
    private String timeOut;
    private int timeout;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getDomain() {

        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ThriftServerModel{");
        sb.append("basePackages=").append(basePackages);
        sb.append(", domain='").append(domain).append('\'');
        sb.append(", port='").append(port).append('\'');
        sb.append(", timeOut='").append(timeOut).append('\'');
        sb.append(", timeout=").append(timeout);
        sb.append('}');
        return sb.toString();
    }
}
