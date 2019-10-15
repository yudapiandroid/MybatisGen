package com.suse.yuxin.sqlgen.gen;

import com.squareup.javapoet.*;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.SQLRegister;
import com.suse.yuxin.sqlgen.util.Utils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class GenDAOJavaFile implements Gen {

    @Override
    public void gen(TableMetaInfo metaInfo, String targetPath, String targetPackage) throws IOException {
        String javaName = Utils.getDAOClassNameByTableName(metaInfo.getTableName());
        Iterator<Map.Entry<String, SqlMetaInfo>> ms = SQLRegister.genSql(metaInfo,targetPackage).entrySet().iterator();

        TypeSpec.Builder daoClass = TypeSpec.interfaceBuilder(javaName)
                .addModifiers(Modifier.PUBLIC);

        while (ms.hasNext()) {
            Map.Entry<String, SqlMetaInfo> entry = ms.next();
            String methodName = entry.getKey();
            SqlMetaInfo m = entry.getValue();
            String anName = "";
            if(m.isSelect()){
                anName = "SelectProvider";
            } else if(m.isInsert()) {
                anName = "InsertProvider";
            } else if (m.isDelete()) {
                anName = "DeleteProvider";
            } else if (m.isUpdate()) {
                anName = "UpdateProvider";
            }
            AnnotationSpec an =
                    AnnotationSpec.builder(ClassName.get("org.apache.ibatis.annotations",anName))
                            .addMember("type","$T.class",
                                    ClassName.get(targetPackage + ".sql",
                                            Utils.getSQLClassNameByTableName(metaInfo.getTableName())))
                            .addMember("method","$S",methodName)
                            .build();
            String boName = Utils.getEntityClassNameByTableName(metaInfo.getTableName());
            TypeName boList = Utils.genListTypeName(targetPackage + ".bo", boName);

            // 处理返回值
            TypeName returnType = null;
            if(m.isSelect()){
                returnType = m.isMulti() ? boList : ClassName.get(targetPackage + ".bo", boName);
            }else if(m.isDelete() || m.isInsert() || m.isUpdate()){
                returnType = ClassName.get("java.lang", "Integer");
            }

            MethodSpec.Builder tm = MethodSpec.methodBuilder(methodName)
                    .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                    .returns(returnType)
                    .addAnnotation(an);
            tm.addParameters(m.getParameterSpecs());
            daoClass.addMethod(tm.build());
        }
        daoClass.addAnnotation(
                ClassName.get("org.apache.ibatis.annotations", "Mapper")
        );
        JavaFile.builder(targetPackage + ".dao", daoClass.build())
                .build().writeTo(new File(targetPath));
    }

}
