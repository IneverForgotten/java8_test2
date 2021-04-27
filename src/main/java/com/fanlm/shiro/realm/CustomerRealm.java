package com.fanlm.shiro.realm;

import com.fanlm.entity.Action;
import com.fanlm.entity.Role;
import com.fanlm.entity.User;
import com.fanlm.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User principal=(User) principals.getPrimaryPrincipal();
        List<Role> userRoles=userService.getUserRoles(principal.getId());
        //添加权限
        Set<String> roles=new HashSet<String>();
        Set<String> permissions=new HashSet<String>();
        if(null!=userRoles){
            for (Role role : userRoles) {
                roles.add(role.getName());
                System.out.println("shiro角色："+role.getName());
                List<Action> ps=userService.getActions(role.getId());
                for (Action p : ps) {
                    System.out.println("shiro菜单权限："+p.getUrl());
                    permissions.add(p.getUrl());
                }
            }
        }
        SimpleAuthorizationInfo info= new SimpleAuthorizationInfo(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String userName = upToken.getUsername();
        User userInfo =userService.queryUserByName(userName);
        if (null == userInfo) {
            throw new UnknownAccountException("用户不存在!");
        }
        Object credentials = userInfo.getPassword();
        ByteSource credentialsSalt=ByteSource.Util.bytes(userInfo.getName());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, credentials,credentialsSalt, getName());
        return authenticationInfo;
    }



    public void clearCached() {
        //清除缓存
        System.out.println("clear Cached");
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}
