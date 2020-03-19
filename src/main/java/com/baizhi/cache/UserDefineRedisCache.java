package com.baizhi.cache;

import com.baizhi.ApplicationContextHold;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserDefineRedisCache implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDefineRedisCache.class);
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;//mapper隐射文件的namespace
    private RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextHold.getBean("redisTemplate");
    private long timeout = 300;

    public UserDefineRedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (value != null) {
            LOGGER.debug("将结果存储cache中 key:" + key + " value:" + value);
            ValueOperations opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key.toString(), value, timeout, TimeUnit.SECONDS);
        }
    }

    @Override
    public Object getObject(Object key) {
        LOGGER.debug("获取cache-key:" + key);
        try {
            if (key != null) {
                Object value = redisTemplate.opsForValue().get(key.toString());
                return value;
            }
        } catch (Exception e) {
            LOGGER.error("cache获取出现异常");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        LOGGER.error("cache-清除key:" + key);
        Object value = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return value;
    }

    @Override
    public void clear() {
        LOGGER.debug("清空所有的缓冲");
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushAll();
                return null;
            }
        });
    }

    @Override
    public int getSize() {
        Long size = (Long) redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
