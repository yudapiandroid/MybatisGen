package com.suse.yuxin.sqlgen.gen.sql;

import com.squareup.javapoet.ParameterSpec;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import java.util.Map;
import java.util.Set;

public interface GenSQL {
    // selectAll => MethodSpec
    void sqlMethod(TableMetaInfo metaInfo,String targetPackage, Map<String, SqlMetaInfo> out);
    boolean isSelect();
    boolean isUpdate();
    boolean isDelete();
    boolean isInsert();
    boolean isMulti();
    void parameters(TableMetaInfo metaInfo,String targetPackage, Set<ParameterSpec> ps);
}
