package com.mybatis.edu.mapper;

import com.mybatis.edu.pojo.User;

import java.util.List;

/**
 * 用户信息持久化接口
 */
public interface UserMapper {

    User getUserById(Integer id);

    List<User> getUserByUserName(String username);

    void insertUser(User user);

}
