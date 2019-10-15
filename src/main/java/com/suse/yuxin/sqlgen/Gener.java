package com.suse.yuxin.sqlgen;


import com.suse.yuxin.sqlgen.db.JDBCTypeHandler;
import com.suse.yuxin.sqlgen.db.TableMetaInfoParser;
import com.suse.yuxin.sqlgen.db.mysql.MySQLJDBCTypeHandler;
import com.suse.yuxin.sqlgen.db.mysql.MySQLTableMetaInfoParse;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;
import com.suse.yuxin.sqlgen.gen.Gen;
import com.suse.yuxin.sqlgen.gen.GenDAOJavaFile;
import com.suse.yuxin.sqlgen.gen.GenEntityJavaFile;
import com.suse.yuxin.sqlgen.gen.GenSQLJavaFile;
import com.suse.yuxin.sqlgen.util.Utils;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * @author: yuxin
 * @date: 2019/10/10
 * @description: 动态生成 Mybatis SQL
 */
public class Gener {

    private static  String JDBC_URL = "jdbc:mysql://localhost:3306/mysite?useSSL=false";
    private static  String USERNAME= "root";
    private static  String PASSWORD = "yuxing2012";

    private static  String TARGET_PACKAGE = "com.suse.yuxin.gen";

    public void gen(String url, String name, String password, String targetPackage) throws Exception {

        JDBC_URL = url;
        USERNAME = name;
        PASSWORD = password;
        TARGET_PACKAGE = targetPackage;

        String targetPath = Utils.getJavaRootPath();// Utils.getPackgeToFilePath(TARGET_PACKAGE);
        System.out.println("targetPath -> "+targetPath);
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

        // mysql 解释器
        TableMetaInfoParser metaInfoParser = new MySQLTableMetaInfoParse();
        List<TableMetaInfo> infos = metaInfoParser.parse(conn);
        // jdbc type -> java type
        convertJDBCType(infos,new MySQLJDBCTypeHandler());
        for (TableMetaInfo table : infos) {
            genJavaFileByTableMetaInfo(table,targetPath);
        }
    }

    private static void convertJDBCType(List<TableMetaInfo> infos,
                                        JDBCTypeHandler typeHandler) throws Exception {
        for (TableMetaInfo info : infos) {
            for (ColumnMetaInfo c : info.getColumns()) {
                c.setJavaType(typeHandler.convertType(c.getType()));
                if(c.getJavaType() == null){
                    throw new Exception(c.getType()+"  不支持");
                }
            }
        }
    }


    private static void genJavaFileByTableMetaInfo(TableMetaInfo table, String targetPath) throws IOException {
        genJavaFile(table, new GenEntityJavaFile(),targetPath, TARGET_PACKAGE);
        genJavaFile(table, new GenSQLJavaFile(), targetPath, TARGET_PACKAGE);
        genJavaFile(table,new GenDAOJavaFile(), targetPath, TARGET_PACKAGE);
    }


    // 生成 java 文件 -> 通过不同的gen实例
    private static void genJavaFile(TableMetaInfo table, Gen gen, String targetPath, String targetPackage) throws IOException {
        gen.gen(table,targetPath, targetPackage);
    }

}
