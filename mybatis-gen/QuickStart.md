### IDEAJ 使用 MyBatis-generator 插件快速生成 MyBatis 代码

1、IDEA创建maven工程

2、 在maven项目的pom.xml 添加mybatis-generator-maven-plugin 插件和MySQL数据库驱动依赖
```
<build>
  <plugins>
    <plugin>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-maven-plugin</artifactId>
      <version>1.3.5</version>
      <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
      </configuration>
    </plugin>
  </plugins>
</build>

<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>5.1.34</version>
</dependency>
```

3、在maven项目下的src/main/resources 目录下建立名为 [generatorConfig.xml](generatorConfig.xml)的配置文件，作为mybatis-generator-maven-plugin 插件的执行目标，模板如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--导入数据库属性配置-->
    <properties resource="datasource.properties"></properties>
    <!-- 配置mysql 驱动jar包路径.用了绝对路径 -->
    <!--<classPathEntry-->
            <!--location="D:/maven_repo/cloudbu_paas_new/mysql/mysql-connector-java/5.1.34/mysql-connector-java-5.1.34.jar"/>-->

    <context id="lpf_mysql_tables" targetRuntime="MyBatis3">
        <property name="javaFileEncoding" value="UTF-8"/>
        <!-- 防止生成的代码中有很多注释，加入下面的配置控制 -->
        <commentGenerator>
            <!--阻止生成注释，默认为false-->
            <property name="suppressAllComments" value="false"/>
            <!--阻止生成的注释包含时间戳，默认为false-->
            <property name="suppressDate" value="true"/>
             <!--开启字段注释-->
            <property name="addRemarkComments" value="true"/>           
        </commentGenerator>

        <!--直接使用数据库连接URL，用户名、密码 -->
        <!--<jdbcConnection driverClass="com.mysql.jdbc.Driver"-->
        <!--connectionURL="jdbc:mysql://127.0.0.1:3306/mydb1?useUnicode=true&amp;characterEncoding=UTF-8&amp;allowMultiQueries=true"-->
        <!--userId="dbuser" password="Admin">-->
        <!--</jdbcConnection>-->
        <!--使用数据库配置文件-->
        <jdbcConnection
                driverClass="${db.driverClassName}"
                connectionURL="${db.url}"
                userId="${db.username}"
                password="${db.password}">
        </jdbcConnection>

        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 数据表对应的model层  -->
        <javaModelGenerator targetPackage="dev.user.entity" targetProject="src/main/java">
            <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
            <property name="enableSubPackages" value="true"/>
            <!-- 是否对model添加 构造函数 -->
            <property name="constructorBased" value="true"/>
            <!-- 是否对类CHAR类型的列的数据进行trim操作 -->
            <property name="trimStrings" value="true"/>
            <!-- 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
            <property name="immutable" value="false"/>
        </javaModelGenerator>

        <!-- mapper映射文件生成所在的目录 为每一个数据库的表生成对应的SqlMap文件 -->
        <sqlMapGenerator targetPackage="dev.user.mapper.xml" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件 的代码
          type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
          type="MIXEDMAPPER",生成基于注解的Java Model 和相应的Mapper对象
          type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口 -->
        <!-- mybatis3中的mapper接口dao生成的位置 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="dev.user.mapper" targetProject="src/main/java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!-- 数据表进行生成操作 schema:相当于库名; tableName:表名; domainObjectName:对应的DO -->
        <table schema="mydb1" tableName="t_user" domainObjectName="User"
               enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false"
               selectByExampleQueryId="false" enableInsert="true"
               selectByPrimaryKeyQueryId="true" enableSelectByPrimaryKey="true">
            <!--设置类字段和数据库字段名大小写一致-->
            <property name="useActualColumnNames" value="true"/>
            <!--设置类字段和数据库字段类型间的映射-->
            <columnOverride column="createTime" jdbcType="DATETIME" javaType="java.sql.Timestamp"/>
            <columnOverride column="updateTime" jdbcType="DATETIME" javaType="java.sql.Timestamp"/>
        </table>
        <!--有另外一张表-->
        <!--<table schema="mydb1" tableName="t_user" domainObjectName="User"-->
        <!--enableCountByExample="false" enableUpdateByExample="false"-->
        <!--enableDeleteByExample="false" enableSelectByExample="false"-->
        <!--selectByExampleQueryId="false">-->
        <!--</table>-->
        <!--生成所有表-->
        <!--<table tableName="%">-->
        <!--<generatedKey column="id" sqlStatement="MySql"/>-->
        <!--</table>-->

    </context>
</generatorConfiguration>
```

4、使用maven运行
 1. 有IDEAJ mybatis-generator-maven-plugin插件：工程名->Plugins->mybatis-generator->mybatis-generator:generate->Run Maven Build
 2. [使用maven命令行 ](http://docs.flycloud.me/docs/MBG/running/runningWithMaven.html)
    `mvn mybatis-generator:generate` 运行不会覆盖之前的
    `mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate`  使MBG覆盖重名的文件
    
5、自动生成的结构如下
```
├─.idea
│  ├─inspectionProfiles
│  └─libraries
├─.mvn
│  └─wrapper
└─src
    ├─main
    │  ├─java
    │  │  └─dev
    │  │      ├─mybatisgen
    │  │      └─term
    │  │          ├─entity
    │  │          └─mapper
    │  │              └─xml
    │  └─resources
    │      ├─static
    │      └─templates
    └─test
        └─java
            └─dev
                └─mybatisgen
```

### 参考
1. MyBatis Generator 英文手册 . http://mybatis.org/generator
1. MyBatis Generator 中文手册 . http://docs.flycloud.me/docs/MBG/running/runningWithMaven.html
1. Intellij IDEA 中使用 MyBatis-generator 自动生成 MyBatis 代码 . https://segmentfault.com/a/1190000009058867
1. MyBatis Generator 详解 . https://www.cnblogs.com/softidea/p/5700722.html
