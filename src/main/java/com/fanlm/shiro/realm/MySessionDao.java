package com.fanlm.shiro.realm;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.fanlm.utils.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class MySessionDao extends EnterpriseCacheSessionDAO {


    int sessiontimeout=1800000;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOps;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        System.out.println("MySessionDao.doCreate();" + sessionId);
        assignSessionId(session, sessionId);
        valOps.set(sessionId, SerializableUtils.serialize(session), sessiontimeout, TimeUnit.MILLISECONDS);
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("MySessionDao.doReadSession();" + sessionId);
        if (valOps.get(sessionId) == null) {
            throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
        }
        return SerializableUtils.deserialize(valOps.get(sessionId).toString());
    }

    @Override
    protected void doUpdate(Session session) {
        System.out.println("MySessionDao.doUpdate();" + session.getId());
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        valOps.set(session.getId(), SerializableUtils.serialize(session), sessiontimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void doDelete(Session session) {
        System.out.println("MySessionDao.doDelete();");
        stringRedisTemplate.delete(session.getId().toString());
    }
}
