# mybatis-edu
MyBatis学习案例

## 1.Jdbc访问数据库的过程
- 1.1 加载数据库驱动
- 1.2 创建数据库连接
- 1.3 创建statement
- 1.4 设置sql语句
- 1.5 设置查询参数
- 1.6 执行查询，得到ResultSet
- 1.7 解析结果集ResultSet
- 1.8 释放资源

## 2.工程搭建
- 2.1 导入依赖jar包
- 2.2 配置SqlMapConfig.xml
- 2.3 配置log4j.properties
- 2.4 创建pojo实体
- 2.5 配置SQL查询的映射文件
- 2.6 加载映射文件

## 3.实现的需求
- 根据用户ID查询用户信息
- 根据用户名查找用户列表
- 添加用户
- 修改用户
- 删除用户
#### 3.1 实现步骤
- 3.1. 编写sql语句
- 3.2. 配置user映射文件
- 3.3. 编写测试程序
#### 3.2 xml映射文件的一些内容
```xml
<mapper namespace="user"></mapper>
```
- namespace：命名空间，用于隔离sql语句，后继有重要重要 
- \#{}：点位符，相当于jdbc的? 
- ${}：字符串拼接指令，如果入参为普通数据类型{}内部只写value
```xml
    <select id="getUserByUserName" parameterType="string" resultType="com.mybatis.edu.pojo.User">
        select
            id,
            username,
            birthday,
            sex,
            address
        from USER
        where username like '%${value}%'
    </select>
```
- id:sql id，语句的唯一标识 
- parameterType:入参的数据类型 
- resultType:返回结果的数据类型
```
   <insert id="insertUser" parameterType="com.mybatis.edu.pojo.User" useGeneratedKeys="true" keyProperty="id">
           
       <selectKey keyProperty="id" resultType="int" order="AFTER">
           SELECT LAST_INSERT_ID()
       </selectKey>
       
       INSERT INTO `user`
           (`username`,
           `birthday`,
           `sex`,
           `address`)
       VALUES (#{username},
               #{birthday},
               #{sex},
               #{address});
   </insert>
```
- selectKey：主键返回
- keyProperty:user中的主键属性
- resultType:主键数据类型
- order:指定selectKey何时执行：AFTER之后

## 4.动态代理Dao
#### 4.1 开发规则
- namespace必需是接口的全路径名
- 接口的方法名必需与映射文件的sql id一致
- 接口的输入参数必需与映射文件的parameterType类型一致
- 接口的返回类型必须与映射文件的resultType类型一致
#### 4.2 开发步骤
- 创建UserMapper.xml映射文件
- 创建UserMapper接口
- 加载UserMapper.xml

## 5. SqlMapConf.xml配置
SqlMapConfig.xml中的配置内容和顺序：
- **properties**（属性） 
- setting（全局配置参数）
- **typeAliases**（类型别名）
- typeHandlers（类型处理器）
- objectFactory（对象工厂）
- plugins（插件）
- environments（环境集合属性对象）
    - environment（环境子属性对象）
        - transactionManager（事务管理）
        - dataSource（数据源） 
- **mappers**（映射器）        
        
#### 5.1 properties    
````xml
<!-- 加载规则，首先加载标签内部属性，再加载外部文件，名称相同时，会替换相同名称的内容 -->
	<properties resource="jdbc.properties">
		<property name="jdbc.username" value="root1"/>
		<property name="jdbc.password" value="root"/>
	</properties>

```` 
#### 5.2 typeAliases 别名
- i.  mybatis默认支持java基本数据类型的别名
- ii. 自定义别名：
````xml
    <typeAliases>
        <!-- 单个别名定义 -->
        <!-- <typeAlias type="com.mybatis.edu.pojo.User" alias="user"/> -->
        <!-- 别名包扫描器(推荐使用此方式)，整个包下的类都被定义别名，别名为类名，不区分大小写-->
        <package name="com.mybatis.edu.pojo"/>
    </typeAliases>
````
#### 5.3 mappers
````xml
    <!-- 加载映射文件 -->
    <mappers>
        <!-- 第一种方式，加载 resource-->
        <mapper resource="mybatis/user.xml"/>

        <!-- 
            第二种方式，class扫描器要求：
            1、映射文件与接口同一目录下
            2、映射文件名必需与接口文件名称一致
        -->
        <mapper class="com.mybatis.edu.mapper.UserMapper"/>


        <!-- 
            第三种方式，包扫描器要求(推荐使用此方式)：
            1、映射文件与接口同一目录下
            2、映射文件名必需与接口文件名称一致
        -->
        <package name="om.mybatis.edu.mapper"/>

    </mappers>
````

