package com.fanlm.redis;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @program: java8_test
 * @description: redis 分布式锁
 * @author: flm
 * @create: 2021-02-19 15:32
 **/
public class RedisTool {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";//意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
    private static final String SET_WITH_EXPIRE_TIME = "PX";//这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。

    /** * 尝试获取分布式锁 * @param jedis Redis客户端 * @param lockKey 锁 * @param requestId 请求标识 * @param expireTime 超期时间 * @return 是否获取成功 */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.setex(lockKey, expireTime, requestId);

        return LOCK_SUCCESS.equals(result);

    }

    private static final Long RELEASE_SUCCESS = 1L;

    /** * 释放分布式锁 * @param jedis Redis客户端 * @param lockKey 锁 * @param requestId 请求标识 * @return 是否释放成功 */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        return RELEASE_SUCCESS.equals(result);

    }
}
