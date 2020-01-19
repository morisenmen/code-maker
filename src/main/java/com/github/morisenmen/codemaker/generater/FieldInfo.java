package com.github.morisenmen.codemaker.generater;

/**
 * 数据库字段封装类
 * @author huzi created at 2019/11/28.
 */
public class FieldInfo {

    /**
     * 数据库字段类型
     **/
    private String  columnType;
    /**
     * java字段类型
     **/
    private String  javaType;
    /**
     * 数据库字段名称
     **/
    private String  columnName;
    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String  beanFiledName;
    /**
     * 数据库字段注释
     **/
    private String  columnComment;
    /**
     * 数据库字段是否允许为空
     **/
    private boolean nullable;
    /**
     * 数据库字段长度
     **/
    private int     columnLength;

    private boolean dim;

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getBeanFiledName() {
        return beanFiledName;
    }

    public void setBeanFiledName(String beanFiledName) {
        this.beanFiledName = beanFiledName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean getNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(int columnLength) {
        this.columnLength = columnLength;
    }

    public boolean isDim() {
        return dim;
    }

    public void setDim(boolean dim) {
        this.dim = dim;
    }
}