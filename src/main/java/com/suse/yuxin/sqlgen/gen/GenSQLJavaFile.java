package com.suse.yuxin.sqlgen.gen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.SQLRegister;
import com.suse.yuxin.sqlgen.util.Utils;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class GenSQLJavaFile implements Gen {

    @Override
    public void gen(TableMetaInfo metaInfo, String targetPath, String targetPackage) throws IOException {
        String javaName = Utils.getSQLClassNameByTableName(metaInfo.getTableName());
        TypeSpec.Builder sqlClass = TypeSpec.classBuilder(javaName)
                .addModifiers(Modifier.PUBLIC);
        Iterator<Map.Entry<String, SqlMetaInfo>> it = SQLRegister.genSql(metaInfo, targetPackage).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, SqlMetaInfo> entry = it.next();
            String name = entry.getKey();
            SqlMetaInfo info = entry.getValue();
            sqlClass.addMethod(info.getMethodSpec());
        }
        JavaFile.builder(targetPackage + ".sql",sqlClass.build())
                .build().writeTo(new File(targetPath));
    }

}
