package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 描述：生成代码的配置信息
 * Created by hushijun on 2018/2/19.
 */
public class Config {
    private String basePackage;
    private String libPath;
    private String module;

    public String getBasePackage() {
        return basePackage;
    }

    @XmlAttribute
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getLibPath() {
        return libPath;
    }

    @XmlAttribute
    public void setLibPath(String libPath) {
        this.libPath = libPath;
    }

    public String getModule() {
        return module;
    }

    @XmlAttribute
    public void setModule(String module) {
        this.module = module;
    }
}
