package com.fanlm.arhthmetic;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @program: java8_test
 * @description: 有效括号 算法
 * @create: 2020-04-08 16:04
 **/
public class ValidBracked {

    /*
    栈先入后出特点恰好与本题括号排序特点一致，即若遇到左括号入栈，遇到右括号时将对应栈顶左括号出栈，则遍历完所有括号后 stack 仍然为空；
    建立哈希表构建左右括号对应关系：key 左括号，value 右括号；
    这样查询 222 个括号是否对应只需 O(1) 时间复杂度；建立栈 stack，遍历字符串 s 并按照算法流程一一判断。
     */

    public static boolean isValid(String s) {
        Map ex = new HashMap<Character , Character>()
        {{put('(',')');put('{','}');put('[',']');}};
        if(s.length()>0 && !ex.containsKey(s.charAt(0))) return false;
        Stack stack = new Stack();
        for(Character  c : s.toCharArray()){
            if(ex.containsKey(c)){
                stack.push(c);
            }else{
                if(c == ex.get(stack.peek())){
                    stack.pop();
                }else{
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }
}
