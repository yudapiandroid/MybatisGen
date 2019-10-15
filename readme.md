#### Mybatis代码生成工具
- 目前仅仅支持Mysql

- 使用的是动态SQL的方式

- 添加依赖
```xml

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>com.github.yudapiandroid</groupId>
            <artifactId>MybatisGen</artifactId>
            <version>v0.3</version>
        </dependency>
    </dependencies>    
```

- 使用方法

```java
    
    import com.suse.yuxin.sqlgen.Gener;
    
    public class Main {
    
        private static final String url = "数据库链接URL";
        private static final String name = "数据库用户名";
        private static final String password = "数据库密码";
        private static final String targetPackage = "生成文件的包名 eg: com.suse.yuxin.base";
            
        public static void main(String[] args) throws Exception {
            new Gener().gen(url, name, password, targetPackage);
        }
    
    }

```