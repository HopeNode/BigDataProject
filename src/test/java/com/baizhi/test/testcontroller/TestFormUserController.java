package com.baizhi.test.testcontroller;

import com.baizhi.UserModelRestApplication;
import com.baizhi.entities.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(classes = UserModelRestApplication.class)
@RunWith(SpringRunner.class)
public class TestFormUserController {

    @Autowired
    private RestTemplate restTemplate;
    private String prefix = "http://localhost:8888/formUserManager";
   /* @PostMapping(value = "/registerUser")
    public User registerUser(User user,
                             @RequestParam(value = "multipartFile",required = false) MultipartFile multipartFile) throws IOException*/

    @Test
    public void testRegisterUser() {
        String url = prefix + "/registerUser";
        //模拟表单数据
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("name", "李⼩四");
        formData.add("password", "123456");
        formData.add("sex", "true");
        formData.add("birthDay", "2018-01-26");
        formData.add("photo", "user.png");
        formData.add("email", "1152926811@qq.com");
        //模拟⽂件上传
        FileSystemResource fileSystemResource = new FileSystemResource("C:/Users/SUN6/Desktop/xxxx.png");
        formData.add("multipartFile", fileSystemResource);
        User user = restTemplate.postForObject(url, formData, User.class);
        System.out.println(user);
    }

    /* @PostMapping(value = "/userLogin")
     public User userLogin(User user)*/
    @Test
    public void testUserLogin() {
        String url = prefix + "/userLogin";
        //模拟表单数据
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("name", "李⼩四");
        formData.add("password", "123456");
        User user = restTemplate.postForObject(url, formData, User.class);
        System.out.println(user);
    }

    /*@PostMapping(value = "/addUser")
    public User addUser(User user,
                        @RequestParam(value = "multipartFile",required = false) MultipartFile multipartFile) throws IOException*/
    @Test
    public void testAddUser() {
        String url = prefix + "/addUser";
        //模拟表单数据
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("name", "王⼩五");
        formData.add("password", "123456");
        formData.add("sex", "true");
        formData.add("birthDay", "2018-01-26");
        formData.add("photo", "user.png");
        formData.add("email", "1152926811@qq.com");
        //模拟⽂件上传
        FileSystemResource fileSystemResource = new FileSystemResource("C:/Users/SUN6/Desktop/xxxx.png");
        formData.add("multipartFile", fileSystemResource);
        User user = restTemplate.postForObject(url, formData, User.class);
        System.out.println(user);
    }


    /*@PutMapping(value = "/updateUser")
    public void updateUser(User user,
                           @RequestParam(value = "multipartFile",required = false) MultipartFile multipartFile) throws IOException*/
    @Test
    public void updateUser() {
        String url = prefix + "/updateUser";
        //模拟表单数据
        LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("id", "5");
        formData.add("sex", "false");
        restTemplate.put(url, formData);
    }

    /*@DeleteMapping(value = "/deleteUserByIds")
    public void delteUserByIds(@RequestParam(value = "ids") Integer[] ids)*/
    @Test
    public void deleteUserByIds() {
        String url = prefix + "/deleteUserByIds?ids={id}";
        Map maps = new HashMap<>();
        maps.put("id", "3,5");
        restTemplate.delete(url, maps);
    }

    /*@GetMapping(value = "/queryUserByPage")
    public List<User> queryUserByPage(@RequestParam(value = "page",defaultValue = "1") Integer pageNow,
                                      @RequestParam(value = "rows",defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "column",required = false) String column,
                                      @RequestParam(value = "value",required = false) String value)*/

    @Test
    public void queryUserByPage() {
        String url = prefix + "/queryUserByPage?page={p}&rows={r}&column={c}&value={v}";
        Map maps = new HashMap<>();
        maps.put("p", "1");
        maps.put("r", "5");
        maps.put("c", "name");
        maps.put("v", "小");
        User[] user = restTemplate.getForObject(url, User[].class, maps);
        System.out.println(user[0]);
    }

   /* @GetMapping(value = "/queryUserCount")
    public Integer queryUserCount(    @RequestParam(value = "column",required = false) String column,
                                      @RequestParam(value = "value",required = false) String value)*/

    @Test
    public void queryUserCount() {
        String url = prefix + "/queryUserCount?column={c}&value={v}";
        Map maps = new HashMap<>();
        maps.put("c", "name");
        maps.put("v", "小");
        Integer i = restTemplate.getForObject(url, Integer.class, maps);
        System.out.println(i);
    }



 /*   @GetMapping(value = "/queryUserById")
    public User queryUserById(@RequestParam(value = "id") Integer id)*/

    @Test
    public void queryUserById() {
        String url = prefix + "/queryUserById?id={id}";
        Map maps = new HashMap<>();
        maps.put("id", "2");
        User i = restTemplate.getForObject(url, User.class, maps);
        System.out.println(i);
    }
}
