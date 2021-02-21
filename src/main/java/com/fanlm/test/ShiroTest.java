package com.fanlm.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

public class ShiroTest {
    public static void main(String[] args) {
        int a = 1;
        int b ;
        b = ++a;

        //*MA5+SALT+1024
        Md5Hash md5Hash = new Md5Hash("123", "123", 1024);
        System.out.println(md5Hash.toHex());


        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        CustomerMd5Realm realm = new CustomerMd5Realm();

        //设置使用hash凭证匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher= new HashedCredentialsMatcher();


        //使用算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列次数
        hashedCredentialsMatcher.setHashIterations(1024);

        realm.setCredentialsMatcher(hashedCredentialsMatcher);

        securityManager.setRealm(realm);

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("wang","123");
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }


        //授权
        if (subject.isAuthenticated()){
            //基于角色
            System.out.println(subject.hasRole("user"));
            System.out.println(subject.hasRoles(Arrays.asList("user","admin1")));
            System.out.println("------------------------");
            //基于权限
            System.out.println(subject.isPermitted("user:*:01"));
            System.out.println(subject.isPermitted("product:create"));

        }

    }
}
