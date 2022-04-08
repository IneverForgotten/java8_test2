package com.fanlm.bean;

import com.fanlm.entity.User;

import java.util.Optional;

/**
 * @ClassName: OptionalTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2021/8/18 10:24
 */
public class OptionalTest {
    public static void main(String[] args) throws Exception {
        User user = null;
        User user2 = null;
        User user3 = new User();
        user = Optional.ofNullable(user).orElse(createUser());
        user2 = Optional.ofNullable(user2).orElseGet(() -> createUser());
        System.out.println(user.getName());
        System.out.println(user2.getName());
        Optional.ofNullable(user3).orElseThrow(()->new Exception("用户不存在"));

        Optional.ofNullable(user)
                .ifPresent(u->{
                    System.out.println(u);
                });
        String has_value = Optional.of("has value").orElse(getDefault());//do invoke 会执行
        String has_value1 = Optional.of("has value").orElseGet(() -> getDefault());// 不会执行
        System.out.println(has_value+"  "+has_value1);

    }
    public static String getDefault() {
        System.out.println("do invoke");
        return "default";
    }
    public static User createUser(){
        User user = new User();
        user.setName("zhangsan");
        return user;
    }
    public User getUser(User user) {
        return Optional.ofNullable(user)
                .filter(u->"zhangsan".equals(u.getName()))
                .orElseGet(()-> {
                    User user1 = new User();
                    user1.setName("zhangsan");
                    return user1;
                });
    }

}
