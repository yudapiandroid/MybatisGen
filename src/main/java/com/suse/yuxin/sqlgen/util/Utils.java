package com.suse.yuxin.sqlgen.util;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.io.File;
import java.io.IOException;

public class Utils {

    /**
    * @description
    * @author  yuxin
    * @date   2019/10/10 14:41
    * @param
    * @return
     *
     *
    *  user -> User  user_info -> UserInfo
    *
    */
    public static String genJavaName(String name, boolean firstLower) {
        if(name.equalsIgnoreCase("long")){
            name = "longx";
        }
        String tempName = name.toLowerCase();
        String[] words = tempName.split("_");
        String result = "";
        for (String w : words) {
            result += firstLetterToUp(w);
        }
        if(firstLower){
            result = result.substring(0,1).toLowerCase() + result.substring(1);
        }
        return result;
    }



    // 仅仅只转换第一个字母
    public static String firstLetterToUp(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }


    // 获取当前工程目录路径
    public static String getCurrentProjectPath() {
        try {
            return new File("").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJavaRootPath() {
        return getCurrentProjectPath()
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "java";
    }

    public static String packgeConvertFilePath(String packge) {
        String result = "";
        for (int i=0;i<packge.length();i++) {
            char c = packge.charAt(i);
            if(c == '.'){
                result += File.separator;
            }else{
                result += c;
            }
        }
        return result;
    }

    public static String getPackgeToFilePath(String packge) {
        return getJavaRootPath() + File.separator + packgeConvertFilePath(packge);
    }


    public static String getEntityClassNameByTableName(String tableName) {
        return genJavaName(tableName,false) + "BO";
    }

    public static String getSQLClassNameByTableName(String tableName) {
        return genJavaName(tableName, false) + "SQL";
    }

    public static String getDAOClassNameByTableName(String tableName) {
        return genJavaName(tableName, false) + "DAO";
    }


    public static TypeName genListTypeName(String targetPackage, String className) {
        ClassName list = ClassName.get("java.util","List");
        ParameterizedTypeName typeName = ParameterizedTypeName.get(list, ClassName.get(targetPackage, className));
        return typeName;
    }


    public static String removeEndStr(String msg, String end) {
        if(msg.endsWith(end)){
            return msg.substring(0, msg.length() - end.length());
        }else{
            return msg;
        }
    }
}
