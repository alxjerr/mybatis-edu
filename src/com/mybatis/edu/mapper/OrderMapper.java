package com.mybatis.edu.mapper;

import com.mybatis.edu.pojo.Order;
import com.mybatis.edu.pojo.OrderUser;

import java.util.List;

/**
 * 订单持久化接口
 */
public interface OrderMapper {

    /**
     * 获取订单列表
     * @return
     */
    List<Order> getOrderList();

    /**
     * resultMap使用
     * @return
     */
    List<Order> getOrderListMap();

    /**
     * 测试一对一关联查询 resultType方式
     * @return
     */
    List<OrderUser> getOrderUser();

    List<Order> getOrderUserResultMap();
}
