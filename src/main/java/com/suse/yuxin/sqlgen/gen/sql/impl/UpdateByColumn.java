package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.UpdateGenSQL;
import com.suse.yuxin.sqlgen.util.Utils;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class UpdateByColumn extends UpdateGenSQL {

    @Override
    public void sqlMethod(TableMetaInfo metaInfo, String targetPackage, Map<String, SqlMetaInfo> out) {
        for (ColumnMetaInfo c : metaInfo.getColumns()) {
            if(c.getName().equalsIgnoreCase(metaInfo.getPrimaryColumn().getName())){
                continue;
            }
            String javaNameLower = Utils.genJavaName(c.getName(), true);
            String javaNameUp = Utils.genJavaName(c.getName(), false);
            String methodName = "updateBy" + javaNameUp;
            SqlMetaInfo info = new SqlMetaInfo();
            info.setUpdate(true);
            info.setMethodSpec(MethodSpec.methodBuilder(methodName)
                    .returns(String.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement(
                            "return new $T().UPDATE($S).SET($S).WHERE($S).toString()",
                            SQLTypeName(),
                            metaInfo.getTableName(),
                            c.getName() + "=#{" + javaNameLower + "}",
                            metaInfo.getPrimaryColumn().getName() + "=#{" + Utils.genJavaName(metaInfo.getPrimaryColumn().getName(),true) + "}"
                    )
                    .build());
            Set<ParameterSpec> ps = new LinkedHashSet<>();
            ps.add(ParameterSpec.builder(
                    metaInfo.getPrimaryColumn().getJavaType(),
                    Utils.genJavaName(metaInfo.getPrimaryColumn().getName(), true)
            ).addAnnotation(
                    AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                            .addMember("value", "$S", Utils.genJavaName(metaInfo.getPrimaryColumn().getName(), true))
                            .build()
            ).build());
            ps.add(
                    ParameterSpec.builder(
                            c.getJavaType(),
                            Utils.genJavaName(c.getName(), true)
                    )
                            .addAnnotation(
                                    AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                                            .addMember("value", "$S", Utils.genJavaName(c.getName(),  true))
                                            .build()
                            )
                            .build()
            );
            info.setParameterSpecs(ps);
            out.put(methodName,info);
        }
    }

}
