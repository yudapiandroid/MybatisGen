package com.suse.yuxin.sqlgen.db.mysql;

import com.suse.yuxin.sqlgen.db.JDBCTypeHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class MySQLJDBCTypeHandler implements JDBCTypeHandler {

    @Override
    public Class convertType(String typeName) {
        if("VARCHAR".equalsIgnoreCase(typeName) ||
                "CHAR".equalsIgnoreCase(typeName) ||
                "LONGTEXT".equalsIgnoreCase(typeName)) {
            return String.class;
        }
        if ("INT".equalsIgnoreCase(typeName) ||
                "BIT".equalsIgnoreCase(typeName) ||
                typeName.contains("SMALLINT")) {
            return Integer.class;
        }
        if("BIGINT".equalsIgnoreCase(typeName)){
            return BigInteger.class;
        }
        if("DATETIME".equalsIgnoreCase(typeName) ||
                "DATE".equalsIgnoreCase(typeName) ||
                "TIME".equalsIgnoreCase(typeName)){
            return Date.class;
        }
        return null;
    }

}
