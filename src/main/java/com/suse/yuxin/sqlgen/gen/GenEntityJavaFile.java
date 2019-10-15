package com.suse.yuxin.sqlgen.gen;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.util.Utils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class GenEntityJavaFile implements Gen{


    @Override
    public void gen(TableMetaInfo metaInfo, String targetPath, String targetPackage) throws IOException {
        String fileName = Utils.getEntityClassNameByTableName(metaInfo.getTableName());
        TypeSpec.Builder builder = TypeSpec.classBuilder(fileName)
                .addSuperinterface(Serializable.class)
                .addModifiers(Modifier.PUBLIC);
        for (ColumnMetaInfo column : metaInfo.getColumns()) {
            FieldSpec columnF = FieldSpec.builder(column.getJavaType(), Utils.genJavaName(column.getName(), true))
                    .addModifiers(Modifier.PRIVATE)
                    .addJavadoc(column.getRemarker())
                    .build();
            builder.addField(columnF);
            // getter
            MethodSpec getter = MethodSpec.methodBuilder("get" + Utils.genJavaName(column.getName(), false))
                    .returns(column.getJavaType())
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("return " + Utils.genJavaName(column.getName(), true)).build();
            builder.addMethod(getter);
            // setter
            MethodSpec setter = MethodSpec.methodBuilder("set" + Utils.genJavaName(column.getName(), false))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(column.getJavaType(),Utils.genJavaName(column.getName(), true))
                    .addStatement("this."
                            + Utils.genJavaName(column.getName(),true) + " = "
                            + Utils.genJavaName(column.getName(),true)).build();
            builder.addMethod(setter);
        }
        JavaFile.builder(targetPackage + ".bo", builder.build()).build().writeTo(new File(targetPath));
    }
}
