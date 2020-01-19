package com.github.morisenmen.codemaker.generater;

/**
 * 数据库字段封装类
 * @author huzi created at 2019/11/28.
 */
public class ColumnClass {

    /**
     * 数据库字段名称
     **/
    private String  columnName;
    /**
     * 数据库字段类型
     **/
    private String  columnType;
    /**
     * 数据库字段首字母小写且去掉下划线字符串
     **/
    private String  changeColumnName;
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

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getChangeColumnName() {
        return changeColumnName;
    }

    public void setChangeColumnName(String changeColumnName) {
        this.changeColumnName = changeColumnName;
    }

    public boolean isNullable() {
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
}