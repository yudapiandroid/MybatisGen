package com.suse.yuxin.sqlgen.entity;

import java.io.Serializable;

public class ColumnMetaInfo implements Serializable {

    private String name;
    private String type;
    private String remarker;

    private Class javaType;

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public String getRemarker() {
        return remarker;
    }

    public void setRemarker(String remarker) {
        this.remarker = remarker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ColumnMetaInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", remarker='" + remarker + '\'' +
                '}';
    }
}
