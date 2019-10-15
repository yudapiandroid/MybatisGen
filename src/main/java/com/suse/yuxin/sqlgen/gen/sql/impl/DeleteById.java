package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.DeleteGenSQL;
import com.suse.yuxin.sqlgen.util.Utils;

import java.util.Set;

public class DeleteById extends DeleteGenSQL {
    @Override
    public String methodName(TableMetaInfo metaInfo) {
        return "deleteById";
    }

    @Override
    public String sql(TableMetaInfo metaInfo) {
        return "return new $T().DELETE_FROM($S).WHERE($S)";
    }

    @Override
    public Object[] genArgs(TableMetaInfo metaInfo) {
        return new Object[]{
                SQLTypeName(),
                metaInfo.getTableName(),
                "id=#{"+Utils.genJavaName(metaInfo.getPrimaryColumn().getName(),true)+"}"
        };
    }

    @Override
    public void parameters(TableMetaInfo metaInfo, String targetPackage, Set<ParameterSpec> ps) {
        ps.add(
                ParameterSpec.builder(
                        metaInfo.getPrimaryColumn().getJavaType(),
                        metaInfo.getPrimaryColumn().getName()
                )
                        .addAnnotation(
                                AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                                        .addMember("value", "$S", Utils.genJavaName(metaInfo.getPrimaryColumn().getName(),  true))
                                        .build()
                        )
                        .build()
        );
    }

}
