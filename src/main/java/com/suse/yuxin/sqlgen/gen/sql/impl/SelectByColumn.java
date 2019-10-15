package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.GenSQLAdapter;
import com.suse.yuxin.sqlgen.util.Utils;

import javax.lang.model.element.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SelectByColumn extends GenSQLAdapter {

    @Override
    public void sqlMethod(TableMetaInfo metaInfo, String targetPackage, Map<String, SqlMetaInfo> out) {
        for (ColumnMetaInfo c : metaInfo.getColumns()) {
            String filedName = Utils.genJavaName(c.getName(), true);
            String methodName = "selectEquals" + Utils.genJavaName(c.getName(), false);
            SqlMetaInfo info = new SqlMetaInfo();
            info.setSelect(true);
            info.setMulti(true);
            info.setMethodSpec(
                    MethodSpec.methodBuilder(methodName)
                            .addStatement(
                                    "return new $T().SELECT($S).FROM($S).WHERE($S).toString()",
                                    SQLTypeName(),
                                    "*",
                                    metaInfo.getTableName(),
                                    c.getName()+"=#{"+Utils.genJavaName(c.getName(), true)+"}"
                                    )
                            .returns(String.class)
                            .addModifiers(Modifier.PUBLIC)
                            .build()
            );
            Set<ParameterSpec> ps = new HashSet<>();
            ps.add(ParameterSpec.builder(c.getJavaType(), filedName).addAnnotation(
                    AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                            .addMember("value", "$S", Utils.genJavaName(c.getName(),  true))
                            .build()
            ).build());
            info.setParameterSpecs(ps);
            out.put(methodName,info);
            // 同时选择两个列
            for (ColumnMetaInfo subC : metaInfo.getColumns()) {
                genTwoColumn(c,subC,metaInfo,out);
            }
        }

        for (ColumnMetaInfo c : metaInfo.getColumns()) {
            if(c.getJavaType() != String.class){
                continue;
            }
            String filedName = Utils.genJavaName(c.getName(), true);
            String methodName = "selectLike" + Utils.genJavaName(c.getName(), false);
            SqlMetaInfo info = new SqlMetaInfo();
            info.setSelect(true);
            info.setMulti(true);
            info.setMethodSpec(
                    MethodSpec.methodBuilder(methodName)
                            .addStatement(
                                    "return new $T().SELECT($S).FROM($S).WHERE($S).toString()",
                                    SQLTypeName(),
                                    "*",
                                    metaInfo.getTableName(),
                                    c.getName()+" LIKE CONCAT('%',#{"+Utils.genJavaName(c.getName(),true)+"},'%')"
                            )
                            .returns(String.class)
                            .addModifiers(Modifier.PUBLIC)
                            .build()
            );
            Set<ParameterSpec> ps = new HashSet<>();
            ps.add(ParameterSpec.builder(c.getJavaType(), filedName).addAnnotation(
                    AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                            .addMember("value", "$S", Utils.genJavaName(c.getName(),  true))
                            .build()
            ).build());
            info.setParameterSpecs(ps);
            out.put(methodName,info);
        }

    }

    private void genTwoColumn(ColumnMetaInfo c, ColumnMetaInfo subC, TableMetaInfo metaInfo, Map<String, SqlMetaInfo> out) {
        if(c.getName().equalsIgnoreCase(metaInfo.getPrimaryColumn().getName()) ||
                subC.getName().equalsIgnoreCase(metaInfo.getPrimaryColumn().getName()) ||
                c.getName().equalsIgnoreCase(subC.getName())){
            return;
        }
        String c1Name = Utils.genJavaName(c.getName(), true);
        String c2Name = Utils.genJavaName(subC.getName(), true);
        String c1NameUp = Utils.genJavaName(c.getName(), false);
        String c2NameUp = Utils.genJavaName(subC.getName(), false);
        String methodName = "selectEquals" + c1NameUp + "And" + c2NameUp;
        SqlMetaInfo info = new SqlMetaInfo();
        info.setSelect(true);
        info.setMulti(true);
        info.setMethodSpec(
                MethodSpec.methodBuilder(methodName)
                        .addStatement(
                                "return new $T().SELECT($S).FROM($S).WHERE($S).WHERE($S).toString()",
                                SQLTypeName(),
                                "*",
                                metaInfo.getTableName(),
                                c.getName()+"=#{"+c1Name+"}",
                                subC.getName() + "=#{"+c2Name+"}"
                        )
                        .returns(String.class)
                        .addModifiers(Modifier.PUBLIC)
                        .build()
        );
        Set<ParameterSpec> ps = new HashSet<>();
        ps.add(ParameterSpec.builder(c.getJavaType(), c1Name).addAnnotation(
                AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                        .addMember("value", "$S", c1Name)
                        .build()
        ).build());
        ps.add(
                ParameterSpec.builder(subC.getJavaType(), c2Name).addAnnotation(
                        AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations", "Param"))
                                .addMember("value", "$S", c2Name)
                                .build()
                ).build()
        );
        info.setParameterSpecs(ps);
        out.put(methodName,info);
    }


    @Override
    public boolean isSelect() {
        return true;
    }

    @Override
    public boolean isMulti() {
        return true;
    }
}
