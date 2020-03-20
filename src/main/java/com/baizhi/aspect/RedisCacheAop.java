package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component  //交由给工厂管理
@Aspect  //声明为切面类
public class RedisCacheAop {
    /*
     * @program: BigDataProject
     *
     * @description: Aop实现MySBatis的二级缓存
     *
     * @author: 周
     *
     * @create date: 2020-03-19 10:18
     **/
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;  //String类型的template
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheAop.class);

    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置key的序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        //判断是否有缓存  有直接返回
        HashOperations hashOperations = redisTemplate.opsForHash();
        // key namespace  key方法名+参数 value值
        String nameSpace = proceedingJoinPoint.getTarget().getClass().getName(); //namespace
        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //获得所有的参数
        Object[] args = proceedingJoinPoint.getArgs();
        //将方法名和参数进行拼接
        StringBuilder sb = new StringBuilder();
        sb.append(methodName);
        for (Object arg : args) {
            sb.append(arg.toString());
        }
        //判断缓存中key是否存在
        if (hashOperations.hasKey(nameSpace, sb)) { //存在
            System.out.println("----------获得缓存数据--------------");
            LOGGER.debug("----------获得缓存数据--------------");
            Object o = hashOperations.get(nameSpace, sb);
            return o;
        }
        //否则 查询数据库
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("----------添加缓存--------------");
        LOGGER.debug("----------添加缓存--------------");
        //添加缓存
        hashOperations.put(nameSpace, sb, proceed);
        return proceed;
    }

    @After("@annotation(com.baizhi.annotation.ClearCache)")
    public void after(JoinPoint joinPoint) {
        //获得key
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("-----------清除缓冲数据----------");
        LOGGER.debug("-----------清除缓冲数据----------");
        stringRedisTemplate.delete(name);
    }
}
