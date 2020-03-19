package com.baizhi.test.commons;

import com.baizhi.UserModelRestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = UserModelRestApplication.class)
@RunWith(SpringRunner.class)
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("name", "zxxx");
    }

}
