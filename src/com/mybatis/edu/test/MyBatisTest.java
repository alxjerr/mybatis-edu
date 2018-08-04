package com.mybatis.edu.test;

import com.mybatis.edu.pojo.User;
import com.mybatis.edu.util.SqlSessionFactoryUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MyBatisTest {

    @Test
    public void testGetUserById() throws IOException {
        //创建核心配置文件的输入流
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建sqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        // 通过输入流创建SQLSessionFactory
        SqlSessionFactory sqlFactory = ssfb.build(inputStream);
        //创建sqlSession对象
        SqlSession sqlSession = sqlFactory.openSession();
        //执行查询
        User user = sqlSession.selectOne("user.getUserById", 1);

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void testGetUserByName(){

        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();

//        List<User> list = sqlSession.selectList("user.getUserByUserName", "%张%");
        List<User> list = sqlSession.selectList("user.getUserByUserName", "张");

        for(User user : list){
            System.out.println(user);
        }

        sqlSession.close();

    }

    @Test
    public void testInsertUser() {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();

        User user = new User();
        user.setUsername("赵子龙");
        user.setSex("1");
        user.setBirthday(new Date());
        user.setAddress("深圳黑马");

        sqlSession.insert("user.insertUser",user);

        sqlSession.commit();
        sqlSession.close();
    }




}
