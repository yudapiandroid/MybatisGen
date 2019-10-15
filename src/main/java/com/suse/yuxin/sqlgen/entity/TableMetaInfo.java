package com.suse.yuxin.sqlgen.entity;

import java.io.Serializable;
import java.util.List;


public class TableMetaInfo implements Serializable {

    private String tableName;
    private List<ColumnMetaInfo> columns;
    private ColumnMetaInfo primaryColumn;

    public ColumnMetaInfo getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(ColumnMetaInfo primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnMetaInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMetaInfo> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TableMetaInfo{" +
                "tableName='" + tableName + '\'' +
                ", columns=" + columns +
                '}';
    }
}
