package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 描述：表配置信息
 * Created by hushijun on 2018/2/19.
 */
@XmlRootElement(name = "field")
public class TableField {
    /**
     * 字段名
     */
    private String  key;
    /**
     * 字段别名
     */
    private String  alias;
    /**
     * 是否模糊搜索
     */
    private boolean dim = false;

    public String getKey() {
        return key;
    }

    @XmlAttribute
    public void setKey(String key) {
        this.key = key;
    }

    public String getAlias() {
        return alias;
    }

    @XmlAttribute
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isDim() {
        return dim;
    }

    @XmlAttribute
    public void setDim(boolean dim) {
        this.dim = dim;
    }
}
