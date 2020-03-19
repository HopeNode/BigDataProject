package com.baizhi.test.service;

import com.baizhi.UserModelRestApplication;
import com.baizhi.entities.User;
import com.baizhi.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = {UserModelRestApplication.class})
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void saveUserTest() {
        User user = new User("高小九2", true, "123456", new Date(), "aa.png", "qq.com");
        userService.saveUser(user);
        assertNotNull("用户ID不为空", user.getId());
    }

    @Test
    public void queryUserByNameAndPasswordTests() {
        User loginUser = new User();
        loginUser.setName("赵小六");
        loginUser.setPassword("123456");

        for (int i = 0; i < 10; i++) {
            userService.queryUserByNameAndPassword(loginUser);
        }

    }

    @Test
    public void queryUserByPageTests() {
        Integer pageNow = 1;
        Integer pageSize = 10;
        String column = "name";
        String value = "小";
        List<User> userList = userService.queryUserByPage(pageNow, pageSize, column, value);

        assertFalse(userList.isEmpty());
    }

    @Test
    public void queryUserCountTest() {
        String column = "name";
        String value = "小";
        Integer count = userService.queryUserCount(column, value);
        assertTrue(count != 0);
    }

    @Test
    public void queryUserById() {
        Integer id = 19;
        User u = userService.queryUserById(id);
        assertNotNull(u);
    }

    @Test
    public void deleteByUserIdsTests() {
        userService.deleteByUserIds(new Integer[]{1, 4, 3});
    }

    @Test
    public void updateUserTests() {
        Integer id = 2;
        User u = userService.queryUserById(id);
        assertNotNull(u.getName());
        u.setSex(false);
        userService.updateUser(u);
        User newUser = userService.queryUserById(id);
        assertEquals("用户sex", false, newUser.getSex());
    }
}
