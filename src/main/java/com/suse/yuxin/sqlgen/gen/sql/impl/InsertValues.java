package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.util.Utils;

import java.util.Set;

public class InsertValues extends InsertObj {

    @Override
    public String methodName(TableMetaInfo metaInfo) {
        return "insertValues";
    }

    @Override
    public void parameters(TableMetaInfo metaInfo, String targetPackage, Set<ParameterSpec> ps) {
        for (ColumnMetaInfo c : metaInfo.getColumns()) {
            ps.add(
                    ParameterSpec.builder(
                            c.getJavaType(),
                            Utils.genJavaName(c.getName(), true)
                    ).addAnnotation(
                            AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                                    .addMember("value", "$S", Utils.genJavaName(c.getName(),  true))
                                    .build()
                    ).build()
            );
        }
    }
}
