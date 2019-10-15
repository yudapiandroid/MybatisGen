package com.suse.yuxin.sqlgen.entity;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.io.Serializable;
import java.util.Set;

public class SqlMetaInfo implements Serializable {
    private String method;
    private MethodSpec methodSpec;

    private Set<ParameterSpec> parameterSpecs;

    private boolean select;
    private boolean insert;
    private boolean update;
    private boolean delete;
    // 是否返回多条数据
    private boolean multi;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public MethodSpec getMethodSpec() {
        return methodSpec;
    }

    public void setMethodSpec(MethodSpec methodSpec) {
        this.methodSpec = methodSpec;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public Set<ParameterSpec> getParameterSpecs() {
        return parameterSpecs;
    }

    public void setParameterSpecs(Set<ParameterSpec> parameterSpecs) {
        this.parameterSpecs = parameterSpecs;
    }
}
