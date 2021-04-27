package com.fanlm.test;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1213");
        list.add("4qee");
        list.add("d4df");
        list.add("v56d");
        String s = list.toString();
        String substring = s.substring(1, s.length() - 1);
        System.out.println(substring);


        String interview = new String("今天下午 5点 面试");
        String[] s1 = interview.split(" ");
        String substring1 = s1[1].substring(0, 1);
        System.out.println(substring1);


        List<User> userWithName = new ArrayList<>();
        List<User> userWithNumber = new ArrayList<>();

        for (User user : userWithName) {
            long id = user.getId();

        }

    }
}
@Data
class User{
    long id;
    long number;
    String name;

}
