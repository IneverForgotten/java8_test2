package com.fanlm.arhthmetic;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidBracked {

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
