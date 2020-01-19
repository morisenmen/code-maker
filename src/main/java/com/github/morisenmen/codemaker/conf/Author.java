package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 描述：作者信息
 * Created by hushijun on 2018/2/19.
 */
public class Author {
    private String username;
    private String email;

    public String getUsername() {
        return username;
    }

    @XmlAttribute
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    @XmlAttribute
    public void setEmail(String email) {
        this.email = email;
    }
}
