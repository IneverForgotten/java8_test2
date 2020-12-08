package com.fanlm.shiro.realm;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class MyShiroSessionListener implements SessionListener {

    //会话创建时触发
    @Override
    public void onStart(Session session) {
        System.out.println("onStart");
    }
    //会话过期时触发
    @Override
    public void onStop(Session session) {
        System.out.println("onStop");
    }
    //退出/会话过期时触发
    @Override
    public void onExpiration(Session session) {
        System.out.println("onExpiration");
    }

}
