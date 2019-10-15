package com.suse.yuxin.sqlgen.gen.sql.impl;


import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.AbstractGenSQL;
import com.suse.yuxin.sqlgen.gen.sql.SelectMultiGenSQL;

/**
 * @author: yuxin
 * @date: 2019/10/10
 * @description: 生成 selectAll selectByPrimaryKey
 */
public class SelectAll extends SelectMultiGenSQL {

    @Override
    public String methodName(TableMetaInfo metaInfo) {
        return "selectAll";
    }

    @Override
    public String sql(TableMetaInfo metaInfo) {
        return "return new $T().SELECT($S).FROM($S)";
    }

    @Override
    public Object[] genArgs(TableMetaInfo metaInfo) {
        return new Object[]{
                SQLTypeName(),
                "*",
                metaInfo.getTableName()
        };
    }
}
