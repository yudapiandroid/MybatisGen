package com.suse.yuxin.sqlgen.gen.sql;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractGenSQL implements GenSQL {

    @Override
    public void sqlMethod(TableMetaInfo metaInfo,String targetPackage, Map<String, SqlMetaInfo> out) {
        MethodSpec ms = MethodSpec.methodBuilder(methodName(metaInfo))
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement(
                        sql(metaInfo).endsWith("toString()") ? sql(metaInfo) : sql(metaInfo) + ".toString()",
                        genArgs(metaInfo))
                .build();
        SqlMetaInfo info = new SqlMetaInfo();
        info.setDelete(isDelete());
        info.setInsert(isInsert());
        info.setMethod(methodName(metaInfo));
        info.setSelect(isSelect());
        info.setUpdate(isUpdate());
        info.setMulti(isMulti());
        info.setMethodSpec(ms);
        Set<ParameterSpec> ps = new HashSet<>();
        parameters(metaInfo,targetPackage, ps);
        info.setParameterSpecs(ps);
        out.put(methodName(metaInfo), info);
    }

    protected TypeName SQLTypeName() {
        return ClassName.get("org.apache.ibatis.jdbc","SQL");
    }

    public abstract String methodName(TableMetaInfo metaInfo);
    public abstract String sql(TableMetaInfo metaInfo);
    public abstract Object[] genArgs(TableMetaInfo metaInfo);



    // default

    @Override
    public void  parameters(TableMetaInfo metaInfo,String targetPackage, Set<ParameterSpec> ps) {
        // NOP
    }

    @Override
    public boolean isDelete() {
        return false;
    }

    @Override
    public boolean isInsert() {
        return false;
    }

    @Override
    public boolean isMulti() {
        return false;
    }

    @Override
    public boolean isSelect() {
        return false;
    }

    @Override
    public boolean isUpdate() {
        return false;
    }
}
