package com.baizhi.controller;

import com.baizhi.entities.User;
import com.baizhi.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @RestController: 等价 @Controller + @ResponseBody
 * @RequestMapping:请求映射，在Spring4.0注解开发中对RequestMapping进行丰富，分别按照请求类型 - @PostMapping   新增
 * - @GetMapping    查询
 * - @PutMapping    修改
 * - @DeleteMapping 删除
 * @RequestParam:接收表单参数
 * @RequestPart：在请求体同时接收 文件和json数据
 * @RequestBody：仅仅接收json数据
 */
@RestController
@RequestMapping(value = "/restUserManager")
public class RestUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestUserController.class);

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/registerUser")
    public User registerUser(@RequestPart(value = "user") User user,
                             @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File tmpFile = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")), suffix);
            System.out.println(tmpFile.getName());
            tmpFile.delete();
        }
        userService.saveUser(user);
        return user;
    }

    @PostMapping(value = "/userLogin")
    public User userLogin(@RequestBody User user) {
        return userService.queryUserByNameAndPassword(user);
    }

    @PostMapping(value = "/addUser")
    public User addUser(@RequestPart(value = "user") User user,
                        @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File tmpFile = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")), suffix);
            System.out.println(tmpFile.getName());
            tmpFile.delete();
        }
        userService.saveUser(user);
        return user;
    }

    @PutMapping(value = "/updateUser")
    public void updateUser(@RequestPart(value = "user") User user,
                           @RequestParam(value = "multipartFile", required = false) MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            File tmpFile = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")), suffix);
            System.out.println(tmpFile.getName());
            tmpFile.delete();
        }
        //更新用户信息
        userService.updateUser(user);
    }

    @DeleteMapping(value = "/deleteUserByIds")
    public void delteUserByIds(@RequestParam(value = "ids") Integer[] ids) {
        userService.deleteByUserIds(ids);
    }

    @GetMapping(value = "/queryUserById")
    public User queryUserById(@RequestParam(value = "id") Integer id) {
        //从数据库中查询
        return userService.queryUserById(id);
    }

    @GetMapping(value = "/queryUserByPage")
    public List<User> queryUserByPage(@RequestParam(value = "page", defaultValue = "1") Integer pageNow,
                                      @RequestParam(value = "rows", defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "column", required = false) String column,
                                      @RequestParam(value = "value", required = false) String value) {

        HashMap<String, Object> results = new HashMap<>();
        results.put("total", userService.queryUserCount(column, value));
        results.put("rows", userService.queryUserByPage(pageNow, pageSize, column, value));
        return userService.queryUserByPage(pageNow, pageSize, column, value);
    }

    @GetMapping(value = "/queryUserCount")
    public Integer queryUserCount(@RequestParam(value = "column", required = false) String column, @RequestParam(value = "value", required = false) String value) {
        return userService.queryUserCount(column, value);
    }

}