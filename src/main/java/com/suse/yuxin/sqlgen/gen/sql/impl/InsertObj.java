package com.suse.yuxin.sqlgen.gen.sql.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.InsertGenSQL;
import com.suse.yuxin.sqlgen.util.Utils;

import java.util.Set;

public class InsertObj extends InsertGenSQL {

    @Override
    public String methodName(TableMetaInfo metaInfo) {
        return "insertObj";
    }

    @Override
    public String sql(TableMetaInfo metaInfo) {
        return "return new $T().INSERT_INTO($S).VALUES($S,$S)";
    }

    @Override
    public Object[] genArgs(TableMetaInfo metaInfo) {
        String values = "";
        String keys = "";
        for (ColumnMetaInfo c : metaInfo.getColumns()) {
            values += c.getName() + ",";
            keys += "#{" + Utils.genJavaName(c.getName(), true) + "},";
        }
        values = Utils.removeEndStr(values, ",");
        keys = Utils.removeEndStr(keys, ",");
        return new Object[]{
                SQLTypeName(),
                metaInfo.getTableName(),
                values,
                keys
        };
    }

    @Override
    public void parameters(TableMetaInfo metaInfo,String targetPackage, Set<ParameterSpec> ps) {
        ps.add(
                ParameterSpec.builder(
                        ClassName.get(
                                targetPackage + ".bo",
                                Utils.getEntityClassNameByTableName(metaInfo.getTableName())
                                ),
                        Utils.genJavaName(Utils.getEntityClassNameByTableName(metaInfo.getTableName()),true)
                        ).build());
    }
}
