package com.fanlm.shiro.realm;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * @author Mr.css
 * @date 2020/1/3
 */
public class ShiroRedisCache implements Cache<String, ValidatingSession> {
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 前缀，用于标识哪些是Shiro的数据，前缀太长，确实会影响查询效率，可以优化
     */
    private String prefix;

    private String getKey(String key){
        return prefix + key;
    }

    ShiroRedisCache(RedisTemplate<String,Object> redisTemplate, String prefix) {
        this.redisTemplate = redisTemplate;
        this.prefix = prefix;
    }

    @Override
    public ValidatingSession get(String key) throws CacheException {
        if (key == null) {
            return null;
        }
        return (ValidatingSession) redisTemplate.opsForValue().get(this.getKey(key));
    }

    @Override
    public ValidatingSession put(String key, ValidatingSession value) throws CacheException {
        if (key == null || value == null) {
            return null;
        }
        redisTemplate.opsForValue().set(this.getKey(key), value);
        return value;
    }

    @Override
    public ValidatingSession remove(String key) throws CacheException {
        if (key == null) {
            return null;
        }
        key = this.getKey(key);
        ValidatingSession value = (ValidatingSession) redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return value;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(this.keys());
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<String> keys() {
        return redisTemplate.keys(prefix + "*");
    }

    @Override
    public Collection<ValidatingSession> values() {
        Set<String> keys = keys();
        List<ValidatingSession> values = new ArrayList<>(keys.size());
        for (String k : keys) {
            values.add((ValidatingSession)redisTemplate.opsForValue().get(k));
        }
        return values;
    }
}
