package com.fanlm.controller;

import com.fanlm.entity.User;
import com.fanlm.utils.Response;
import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotBlank;

@Controller
public class LoginController {





    @Data
    private class LoginRequest{
        @NotBlank(message = "用户名不能为空！")
        String name;
        @NotBlank(message = "密码不能为空！")
        String password;
    }

    @PostMapping("/checkLogin")
    public Response login(@RequestBody LoginRequest request){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(request.name,request.password);
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误");
        }catch (UnknownAccountException e){
            e.printStackTrace();
            System.out.println("用户名错误");
        }

        return new Response();
    }

    @PostMapping("/logout")
    public Response logout(){
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new Response();
    }
    @PostMapping("/register")
    public Response register(@RequestBody User user){

        Md5Hash md5Hash = new Md5Hash(user.getPassword(), "123", 1024);
        String password = md5Hash.toHex();

        user.setPassword(password);
        user.setSalt("123");

        return new Response();
    }
}
