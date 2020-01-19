package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 描述：数据库连接配置
 * Created by hushijun on 2018/2/19.
 */
public class JdbcConnection {
    private String driver;
    private String url;
    private String user;
    private String password;

    public String getDriver() {
        return driver;
    }

    @XmlAttribute
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    @XmlAttribute
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    @XmlAttribute
    public void setUser(String user) {
        this.user = user;
    }

    @XmlAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
