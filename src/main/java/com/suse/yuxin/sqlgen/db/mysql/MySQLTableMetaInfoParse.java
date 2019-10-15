package com.suse.yuxin.sqlgen.db.mysql;

import com.suse.yuxin.sqlgen.db.TableMetaInfoParser;
import com.suse.yuxin.sqlgen.entity.ColumnMetaInfo;
import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLTableMetaInfoParse implements TableMetaInfoParser {

    @Override
    public List<TableMetaInfo> parse(Connection conn) {
        List<TableMetaInfo> infos = new ArrayList<>();
        try {
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables =
                    dbMeta.getTables(
                            null,
                            null,
                            null,
                            new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                ResultSet columns = dbMeta.getColumns(
                        null,
                        "%",
                        tableName,
                        "%");

                List<ColumnMetaInfo> cos = new ArrayList<>();
                TableMetaInfo tableInfo = new TableMetaInfo();
                tableInfo.setTableName(tableName);
                tableInfo.setColumns(cos);
                // pk name
                ResultSet pks = dbMeta.getPrimaryKeys(null, null, tableName);
                pks.next();
                String pkName = pks.getString("COLUMN_NAME");
                while (columns.next()) {
                    ColumnMetaInfo ci = new ColumnMetaInfo();
                    ci.setName(columns.getString("COLUMN_NAME"));
                    ci.setType(columns.getString("TYPE_NAME"));
                    ci.setRemarker(columns.getString("REMARKS"));
                    cos.add(ci);
                    if(pkName.equalsIgnoreCase(ci.getName())){
                        tableInfo.setPrimaryColumn(ci);
                    }
                }
                infos.add(tableInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infos;
    }

}
