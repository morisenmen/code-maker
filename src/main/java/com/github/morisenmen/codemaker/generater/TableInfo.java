package com.github.morisenmen.codemaker.generater;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库字段封装类
 * @author huzi created at 2019/11/28.
 */
public class TableInfo {

    /**
     * 数据库主键字段类型
     **/
    private String          primaryKeyType;
    /**
     * 数据库主键字段名称
     **/
    private String          primaryKeyName;
    /**
     * 数据库主键bean名称,字段首字母小写且去掉下划线字符串
     **/
    private String          primaryKeyBeanName;
    /**
     * 数据库表备注
     **/
    private String          comment;
    /**
     * 数据库表字段列表
     **/
    private List<FieldInfo> fileds;

    public String getPrimaryKeyType() {
        return primaryKeyType;
    }

    public void setPrimaryKeyType(String primaryKeyType) {
        this.primaryKeyType = primaryKeyType;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public String getPrimaryKeyBeanName() {
        return primaryKeyBeanName;
    }

    public void setPrimaryKeyBeanName(String primaryKeyBeanName) {
        this.primaryKeyBeanName = primaryKeyBeanName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<FieldInfo> getFileds() {
        return fileds;
    }

    public void setFileds(List<FieldInfo> fileds) {
        this.fileds = fileds;
    }

    public void addField(FieldInfo fieldInfo) {
        if (null == fileds) {
            fileds = new ArrayList<>();
        }
        fileds.add(fieldInfo);
        ;
    }
}