package com.suse.yuxin.sqlgen.gen.sql;

public abstract class SelectMultiGenSQL extends SelectOneGenSQL {

    @Override
    public boolean isMulti() {
        return true;
    }
}
