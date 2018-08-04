# mybatis-edu
MyBatis学习案例

##1.Jdbc访问数据库的过程
- 1.1 加载数据库驱动
- 1.2 创建数据库连接
- 1.3 创建statement
- 1.4 设置sql语句
- 1.5 设置查询参数
- 1.6 执行查询，得到ResultSet
- 1.7 解析结果集ResultSet
- 1.8 释放资源

##2.工程搭建
- 2.1 导入依赖jar包
- 2.2 配置SqlMapConfig.xml
- 2.3 配置log4j.properties
- 2.4 创建pojo实体
- 2.5 配置SQL查询的映射文件
- 2.6 加载映射文件

##3.实现的需求
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

##4.动态代理Dao
#### 4.1 开发规则
- namespace必需是接口的全路径名
- 接口的方法名必需与映射文件的sql id一致
- 接口的输入参数必需与映射文件的parameterType类型一致
- 接口的返回类型必须与映射文件的resultType类型一致
#### 4.2 开发步骤
- 创建UserMapper.xml映射文件
- 创建UserMapper接口
- 加载UserMapper.xml

##5. SqlMapConf.xml配置
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
        
####5.1 properties    
````xml
<!-- 加载规则，首先加载标签内部属性，再加载外部文件，名称相同时，会替换相同名称的内容 -->
	<properties resource="jdbc.properties">
		<property name="jdbc.username" value="root1"/>
		<property name="jdbc.password" value="root"/>
	</properties>

```` 
####5.2 typeAliases 别名
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
####5.3 mappers
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
## 6. parameterType 输入类型
#### 6.1 简单类型

#### 6.2 pojo对象
QueryVo.java
`parameterType="queryvo"`

#### 6.3 pojo包装对象


## 7. resultMap 输出类型

#### 7.1 定义resultMap：
映射字段与数据库中一致的可以不用定义
```xml
<resultMap type="order" id="order_list_map">

    <!-- <id>用于映射主键 -->
    <id property="id" column="id"/>
    
    <!-- 普通字段用<result>映射 -->
    <result property="userId" column="user_id"/>
    <result property="number" column="number"/>
    <result property="createtime" column="createtime"/>
    <result property="note" column="note"/>
</resultMap>
```

#### 7.2 使用：
```xml
    <select id="getOrderListMap" resultMap="order_list_map">
        SELECT
            `id`,
            `user_id`,
            `number`,
            `createtime`,
            `note`
        FROM `order`
    </select>
```
## 8.动态SQL
#### 8.1 \<where> / \<if/>使用
```xml
    <!-- <where>自动补上where关键字，同时处理多余and,用了where标签就不能再手动加上where关键字 -->
    <where>
        <if test="username != null and username != ''">
            and username LIKE '%${username}%'
        </if>
        <if test="sex != null and sex != ''">
            and sex = #{sex}
        </if>
    </where>
```

#### 8.2 SQL片段
- 定义SQL片段：
```xml
    <!-- SQL片段定义 -->
    <sql id="user_sql">
      id,
      username,
      birthday,
      sex,
      address
    </sql>
```
- 使用SQL片段：
```xml
    <select id="getUserById" parameterType="int" resultType="com.mybatis.edu.pojo.User">
        select
        
        <!-- sql 片段使用：引用定义好的SQL片段的id -->
        <include refid="user_sql"/>
        
        from USER
        where id = #{id2}
    </select>
```
#### 8.3 \<foreach/> 标签
```xml
    <where>
        <!-- foreach循环标签
             collection:要遍历的集合
             open:循环开始之前输出的内容
             item:设置循环变量
             separator:分隔符
             close:循环结束之后输出的内容 -->
        <!-- 目的：id IN(1,25,29,30,35) -->
        <foreach collection="ids" open="id IN(" item="uId" separator=","
                 close=")">
            #{uId}
        </foreach>
    </where>
```
## 9.关联查询
#### 9.1 一对一关联
##### 9.1.1 使用resultType
新建OrderUser的pojo，继承自Order:
```java
public class OrderUser extends Order {
	
	private String username;
	private String address;
    setter();
    getter();
}

```
OrderMapper.xml新增查询方法`getOrderUser`:
```xml
    <select id="getOrderUser" resultType="orderuser">
        SELECT
            o.`id`,
            o.`user_id` userId,
            o.`number`,
            o.`createtime`,
            o.`note`,
            u.`username`,
            u.`address`
        FROM `order` o
        LEFT JOIN `user` u
        ON u.id = o.`user_id`
    </select>
```
##### 9.1.2 使用resultMap
`<association/>`:配置一对一关系
property:绑定的用户属性
javaType:属性数据类型，支持别名
```xml
    <!-- 一对一关联查询-resultMap -->
    <resultMap type="order" id="order_user_map">
        <!-- id标签用于绑定主键 -->
        <id property="id" column="id"/>
        <!-- 使用result绑定普通字段 -->
        <result property="userId" column="user_id"/>
        <result property="number" column="number"/>
        <result property="createtime" column="createtime"/>
        <result property="note" column="note"/>

       
        <association property="user" javaType="com.mybatis.edu.pojo.User">
            <id property="id" column="user_id"/>

            <result property="username" column="username"/>
            <result property="address" column="address"/>
            <result property="sex" column="sex"/>
        </association>
    </resultMap>
```
#### 9.1 一对多关联查询
`<collection/>` : 配置一对多关系
property:用户下的order属性
ofType:property的数据类型，支持别名

```xml
    <resultMap type="user" id="user_order_map">
        <id property="id" column="id" />
        <result property="username" column="username" />
        <result property="birthday" column="birthday" />
        <result property="address" column="address" />
        <result property="sex" column="sex" />
        <result property="uuid2" column="uuid2" />

        <!-- collection:配置一对多关系
             property:用户下的order属性
             ofType:property的数据类型，支持别名
        -->
        <collection property="orders" ofType="order">
            <!-- id标签用于绑定主键 -->
            <id property="id" column="oid"/>
            <!-- 使用result绑定普通字段 -->
            <result property="userId" column="id"/>
            <result property="number" column="number"/>
            <result property="createtime" column="createtime"/>
        </collection>
    </resultMap>
```
一对多关联查询
```xml
    <select id="getUserOrder" resultMap="user_order_map">
		SELECT
            u.`id`,
            u.`username`,
            u.`birthday`,
            u.`sex`,
            u.`address`,
            u.`uuid2`,
            o.`id` oid,
            o.`number`,
            o.`createtime`
		FROM `user` u
		LEFT JOIN `order` o
		ON o.`user_id` = u.`id`
	</select>
```

