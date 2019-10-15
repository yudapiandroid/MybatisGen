package com.suse.yuxin.sqlgen.gen.sql;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import java.util.Map;
import java.util.Set;

public abstract class GenSQLAdapter implements GenSQL {

    protected TypeName SQLTypeName() {
        return ClassName.get("org.apache.ibatis.jdbc","SQL");
    }


    @Override
    public boolean isSelect() {
        return false;
    }

    @Override
    public boolean isUpdate() {
        return false;
    }

    @Override
    public boolean isDelete() {
        return false;
    }

    @Override
    public boolean isInsert() {
        return false;
    }

    @Override
    public boolean isMulti() {
        return false;
    }

    @Override
    public void parameters(TableMetaInfo metaInfo, String targetPackage, Set<ParameterSpec> ps) {

    }
}
