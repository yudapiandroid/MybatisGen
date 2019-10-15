package com.suse.yuxin.sqlgen.gen.sql;

public abstract class InsertGenSQL extends AbstractGenSQL {
    @Override
    public boolean isInsert() {
        return true;
    }
}
