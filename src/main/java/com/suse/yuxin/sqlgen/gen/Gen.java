package com.suse.yuxin.sqlgen.gen;

import com.suse.yuxin.sqlgen.entity.TableMetaInfo;

import java.io.IOException;

public interface Gen {

    void gen(TableMetaInfo metaInfo, String targetPath, String targetPackage) throws IOException;

}
