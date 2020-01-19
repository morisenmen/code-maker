package com.github.morisenmen.codemaker.conf;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 描述：表配置信息
 * Created by hushijun on 2018/2/19.
 */
@XmlRootElement(name = "table")
public class Table {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表的别名
     */
    private String alias;
    /**
     * 表的描述，比建表的更简短说明
     */
    private String desc;
    /**
     * 表的主键名
     */
    private String key;
    /**
     * 表主键的类型
     */
    private String keyType;
    /**
     * 时间范围查询的名字，默认不按照范围查询
     */
    private String rangeKey = "false";
    /**
     * 指定需要生成的包名
     */
    private String packages;
    /**
     * 是否物理删除
     */
    private boolean realDelete = false;

    /**
     * 字段说明
     */
    private List<TableField> filedList;

    public String getTableName() {
        return tableName;
    }

    @XmlAttribute
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    @XmlAttribute
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDesc() {
        return desc;
    }

    @XmlAttribute
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    @XmlAttribute
    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyType() {
        return keyType;
    }

    @XmlAttribute
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getRangeKey() {
        return rangeKey;
    }

    @XmlAttribute
    public void setRangeKey(String rangeKey) {
        this.rangeKey = rangeKey;
    }

    public String getPackages() {
        return packages;
    }

    @XmlAttribute
    public void setPackages(String packages) {
        this.packages = packages;
    }

    public boolean isRealDelete() {
        return realDelete;
    }

    @XmlAttribute
    public void setRealDelete(boolean realDelete) {
        this.realDelete = realDelete;
    }

    public List<TableField> getFiledList() {
        return filedList;
    }

    @XmlElements(value = {@XmlElement(name = "field", type = TableField.class)})
    public void setFiledList(List<TableField> filedList) {
        this.filedList = filedList;
    }
}
