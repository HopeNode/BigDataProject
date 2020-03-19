package com.baizhi.service;

import com.baizhi.entities.User;

import java.util.List;

public interface IUserService {
    /**
     * 保存用户
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 根据密码和用户名查询用户
     *
     * @param user
     * @return
     */
    User queryUserByNameAndPassword(User user);

    /***
     *
     * @param pageNow
     * @param pageSize
     * @param column 模糊查询列
     * @param value  模糊值
     * @return
     */
    List<User> queryUserByPage(Integer pageNow, Integer pageSize, String column, Object value);

    /**
     * 查询用户总记录
     *
     * @param column
     * @param value
     * @return
     */
    int queryUserCount(String column, Object value);

    /**
     * 根据ID查询用户信息
     *
     * @param id
     * @return
     */
    User queryUserById(Integer id);

    /**
     * 根据IDS删除用户
     *
     * @param ids
     */
    void deleteByUserIds(Integer[] ids);

    /**
     * 更新用户信息
     *
     * @param user
     */
    void updateUser(User user);
}
