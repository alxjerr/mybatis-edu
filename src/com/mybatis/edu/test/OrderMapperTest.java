package com.mybatis.edu.test;

import com.mybatis.edu.mapper.OrderMapper;
import com.mybatis.edu.pojo.Order;
import com.mybatis.edu.pojo.OrderUser;
import com.mybatis.edu.util.SqlSessionFactoryUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OrderMapperTest {
    @Test
    public void getOrderList() throws Exception {
    }


    @Test
    public void getOrderListMap(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orders = mapper.getOrderListMap();

        for (Order order : orders){
            System.out.println(order);
        }
        sqlSession.close();
    }

    @Test
    public void getOrderUser(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<OrderUser> list = mapper.getOrderUser();
        for(OrderUser ou : list){
            System.out.println(ou);
        }
        sqlSession.close();
    }

    @Test
    public void getOrderUserResultMap(){
        SqlSession sqlSession = SqlSessionFactoryUtil.getSqlSessionFactory().openSession();
        OrderMapper mapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> list = mapper.getOrderUserResultMap();
        for(Order order : list){
            System.out.println(order);
            System.out.println("      此订单的用户为：" + order.getUser());
        }
        sqlSession.close();
    }
}