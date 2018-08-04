package com.mybatis.edu.mapper;

import com.mybatis.edu.pojo.QueryVo;
import com.mybatis.edu.pojo.User;

import java.util.List;

/**
 * 用户信息持久化接口
 */
public interface UserMapper {

    User getUserById(Integer id);

    List<User> getUserByUserName(String username);

    void insertUser(User user);

    /**
     * 传递包装pojo
     * @param vo
     * @return
     */
    List<User> getUserByQueryVo(QueryVo vo);

    /**
     * 测试动态SQL标签
     * @param user
     * @return
     */
    List<User> getUserByPojo(User user);

    /**
     * foreach标签测试
     * @param vo
     * @return
     */
    List<User> getUserByIds(QueryVo vo);

    /**
     * 一对多关联查询
     * @return
     */
    List<User> getUserOrder();
}
