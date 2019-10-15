package com.suse.yuxin.sqlgen.gen.sql;

import com.squareup.javapoet.MethodSpec;
import com.suse.yuxin.sqlgen.entity.SqlMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.sql.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLRegister {

    private static List<GenSQL> sqls = new ArrayList<>();

    static {
        sqls.add(new SelectAll());
        sqls.add(new SelectById());
        sqls.add(new InsertObj());
        sqls.add(new InsertValues());
        sqls.add(new DeleteById());
        sqls.add(new SelectByColumn());
        sqls.add(new UpdateByColumn());
    }

    public static Map<String,SqlMetaInfo> genSql(TableMetaInfo metaInfo,String targetPackage) {
        Map<String, SqlMetaInfo> ms = new HashMap<>();
        for (GenSQL sql : sqls) {
            sql.sqlMethod(metaInfo,targetPackage, ms);
        }
        return ms;
    }

}
