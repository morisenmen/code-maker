package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 模板配置
 *
 * @author huzi created at 2019/11/21.
 */
public class FreeMakerTemplate {
    private String path;

    public String getPath() {
        return path;
    }

    @XmlAttribute
    public void setPath(String path) {
        this.path = path;
    }
}
