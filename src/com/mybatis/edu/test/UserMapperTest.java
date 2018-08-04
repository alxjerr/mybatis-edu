package com.mybatis.edu.test;

import com.mybatis.edu.mapper.UserMapper;
import com.mybatis.edu.pojo.Order;
import com.mybatis.edu.pojo.QueryVo;
import com.mybatis.edu.pojo.User;
import com.mybatis.edu.util.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
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


    @Test
    public void testGetUserByQueryVo() {
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("张");
        vo.setUser(user);
        List<User> users = mapper.getUserByQueryVo(vo);
        for(User u : users){
            System.out.println(u);
        }

        sqlSession.close();
    }

    @Test
    public void testGetUserByPojo(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("张");
        user.setSex("1");

        List<User> userList = mapper.getUserByPojo(user);

        for (User u : userList) {
            System.out.println(u);
        }

        sqlSession.close();
    }

    @Test
    public void testGetUserByIds(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        QueryVo vo = new QueryVo();
        vo.setIds(Arrays.asList(1,25,29,30,35));
        List<User> userList = mapper.getUserByIds(vo);

        for (User u : userList) {
            System.out.println(u);
        }
        sqlSession.close();
    }

    @Test
    public void getUserOrder(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.getUserOrder();

        for (User user : list) {
            System.out.println(user);
            for (Order order : user.getOrders()) {
                if(order.getId() != null){
                    System.out.println("      此用户下的订单有：" + order);
                }
            }
        }

        sqlSession.close();
    }


}
