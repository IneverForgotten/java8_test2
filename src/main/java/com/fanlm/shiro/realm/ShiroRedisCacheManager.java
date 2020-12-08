package com.fanlm.shiro.realm;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Mr.css
 * @date 2020/1/3
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {

    private RedisTemplate<String,Object> redisTemplate;

    public ShiroRedisCacheManager(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Cache createCache(String name) throws CacheException {
        return new ShiroRedisCache(redisTemplate,name);
    }
}