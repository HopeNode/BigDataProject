package com.baizhi.dao;

import com.baizhi.entities.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserDAO {

    void saveUser(User user);

    User queryUserByNameAndPassword(User user);

    User queryUserById(Integer id);

    void deleteByUserId(Integer[] ids);

    List<User> queryUserByPage(@Param(value = "pageNow") Integer pageNow, @Param(value = "pageSize") Integer pageSize, @Param(value = "column") String column, @Param(value = "value") Object value);

    int queryCount(@Param(value = "column") String column, @Param(value = "value") Object value);

    void updateUser(User user);
}
