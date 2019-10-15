package com.suse.yuxin.sqlgen.gen.sql;


public abstract class UpdateGenSQL extends GenSQLAdapter {
    @Override
    public boolean isUpdate() {
        return true;
    }
}
