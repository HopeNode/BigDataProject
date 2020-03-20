package com.baizhi.service.impl;

import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.ClearCache;
import com.baizhi.annotation.SlaveDB;
import com.baizhi.dao.IUserDAO;
import com.baizhi.entities.User;
import com.baizhi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserService implements IUserService {

    @Autowired
    private IUserDAO IUserDAO;

    @ClearCache
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void saveUser(User user) {
        IUserDAO.saveUser(user);
    }

    @AddCache
    @SlaveDB
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserByNameAndPassword(User user) {
        return IUserDAO.queryUserByNameAndPassword(user);
    }

    @AddCache
    @SlaveDB
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> queryUserByPage(Integer pageNow, Integer pageSize, String column, Object value) {
        return IUserDAO.queryUserByPage(pageNow, pageSize, column, value);
    }
    @AddCache
    @SlaveDB
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int queryUserCount(String column, Object value) {
        return IUserDAO.queryCount(column, value);
    }

    @AddCache
    @SlaveDB
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserById(Integer id) {
        return IUserDAO.queryUserById(id);
    }

    @ClearCache
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void deleteByUserIds(Integer[] ids) {
        IUserDAO.deleteByUserId(ids);
    }

    @ClearCache
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void updateUser(User user) {
        IUserDAO.updateUser(user);
    }

}
