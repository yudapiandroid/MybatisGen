#### Mybatis代码生成工具
- 目前仅仅支持Mysql

- 使用的是动态SQL的方式

- 使用方法

```java
    
    private static final url = "数据库链接URL";
    public static final name = "数据库用户名";
    public static final password = "数据库密码";
    public static final targetPackage = "生成文件的包名 eg: com.suse.yuxin.base";
    
    public static void main(String[] args) {
        new Gener().gen(url, name, password, targetPackage);
    }

```