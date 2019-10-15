package com.suse.yuxin.sqlgen.db;

import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import java.sql.Connection;
import java.util.List;

public interface TableMetaInfoParser {

    List<TableMetaInfo> parse(Connection conn);

}
