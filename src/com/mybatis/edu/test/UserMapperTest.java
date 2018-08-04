package com.mybatis.edu.test;

import com.mybatis.edu.mapper.UserMapper;
import com.mybatis.edu.pojo.User;
import com.mybatis.edu.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserMapperTest {
    @Test
    public void getUserById() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(10);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void getUserByUserName() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUserByUserName("陈");
        for(User user : users){
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void insertUser() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("诸葛孔明");
        user.setSex("1");
        user.setBirthday(new Date());
        user.setAddress("山东济南");

        mapper.insertUser(user);
        sqlSession.commit();
        sqlSession.close();
    }


}
