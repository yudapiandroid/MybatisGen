package com.suse.yuxin.sqlgen.db;

public interface JDBCTypeHandler {

    Class convertType(String typeName);

}
