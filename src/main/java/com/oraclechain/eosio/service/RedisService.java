package com.oraclechain.eosio.service;

public interface RedisService {



    public String get(String key);
    public void delete(String key);

    public void set(String key,String value);
    public void set(String key,String value,long expireTime);
    public void set(String key, Object value);
    public void set(String key, Object value, long expireTime);
    public void setnx(String key, Object value, long expireTime);
    public boolean exists(final String key);


    /**
     * 获取key所对应的对象
     * @param key
     * @param clazz 目标对象
     * @param <T> 泛型
     * @return
     */
    public <T> T get(String key, Class<T> clazz);


}
