package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisService
 *
 * @author Daydreamer
 * @date 2018/8/17 19:44
 */

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 获取单个对象
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;//返回redis
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;//返回redis
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0){
                return false;
            }
            //生成真正的key，把prefix和key拼了一下
            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0){
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            jedis.set(realKey, beanToString(value));
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> T stringToBean(String str, Class<T> clazz){
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        } else if (clazz == String.class){
            return (T)str;
        } else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private <T> String beanToString(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return "" + value;
        } else if (clazz == String.class){
            return (String)value;
        } else if (clazz == long.class || clazz == Long.class){
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private void returnToPool(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }
}
