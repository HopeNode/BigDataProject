package com.baizhi.test.testcontroller;

import com.baizhi.UserModelRestApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(classes = UserModelRestApplication.class)
@RunWith(SpringRunner.class)
public class TestRestUserController {

    @Autowired
    private RestTemplate restTemplate;
    private String prefix = "http://localhost:8888/formUserManager";

}
