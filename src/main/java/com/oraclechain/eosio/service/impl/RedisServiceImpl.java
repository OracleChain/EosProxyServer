package com.oraclechain.eosio.service.impl;

import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {


    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public String get(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o==null){
            return null;
        }
        return o.toString();
    }



    @Override
    public void set(String key, String value)
    {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void set(String key, String value, long expireTime)
    {
        redisTemplate.opsForValue().set(key,value,expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, Object value)
    {
        redisTemplate.opsForValue().set(key, JsonUtils.objectToJson(value));
    }

    @Override
    public void set(String key, Object value, long expireTime)
    {
        redisTemplate.opsForValue().set(key,JsonUtils.objectToJson(value),expireTime,TimeUnit.SECONDS );
    }


    @Override
    public void setnx(String key, Object value, long expireTime)
    {
        Object o = redisTemplate.opsForValue().get(key);
        if (o==null){
            redisTemplate.opsForValue().set(key,JsonUtils.objectToJson(value),expireTime,TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean exists(String key)
    {
        return redisTemplate.hasKey(key);
    }








    @Override
    public <T> T get(String key, Class<T> clazz)
    {
        String s = get(key);
        if (s == null) {
            return null;
        }
        return JsonUtils.jsonToObject(s,clazz);
    }




}
