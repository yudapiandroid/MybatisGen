package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.SelectOneGenSQL;
import com.suse.yuxin.sqlgen.util.Utils;

import java.util.Set;

public class SelectById extends SelectOneGenSQL {
    @Override
    public String methodName(TableMetaInfo metaInfo) {
        return "selectById";
    }

    @Override
    public String sql(TableMetaInfo metaInfo) {
        return "return new $T().SELECT($S).FROM($S).WHERE($S)";
    }

    @Override
    public Object[] genArgs(TableMetaInfo metaInfo) {
        return new Object[]{
                SQLTypeName(),
                "*",
                metaInfo.getTableName(),
                metaInfo.getPrimaryColumn().getName() + "=#{"+
                        Utils.genJavaName(metaInfo.getPrimaryColumn().getName(),true)
                        +"}"
        };
    }

    @Override
    public void parameters(TableMetaInfo metaInfo,String targetPackage, Set<ParameterSpec> ps) {
        ps.add(ParameterSpec.builder(Integer.class,metaInfo.getPrimaryColumn().getName()).addAnnotation(
                AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                        .addMember("value", "$S", Utils.genJavaName(metaInfo.getPrimaryColumn().getName(),  true))
                        .build()
        ).build());
    }
}
