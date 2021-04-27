package com.fanlm.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MTTest {
    public static void main(String[] args) {
        String s = "In school and life, the most important driving force of work is the pleasure in work, the pleasure of working as a result, and the recognition of the social value of this result. ";
        String[] array = s.split(" ");
        int minCount = 0;
        String indexString= "";
        Map<String,Integer> resultMap = new LinkedHashMap<>();
        Map<String,Integer> allMap = new HashMap<>();
        for(int i = 0; i<array.length ; i++){
            String arr = array[i].toLowerCase();
            array[i] = arr ;
            if (allMap.containsKey(array[i])){
                allMap.put(array[i],allMap.get(array[i])+1);
            }else {
                allMap.put(array[i],1);
            }
        allMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEachOrdered(x -> resultMap.put(x.getKey(), x.getValue()));
            int count = 0 ;
        for (Map.Entry<String ,Integer> entry : resultMap.entrySet()){
            System.out.println(entry.getKey()+"="+entry.getValue());
            count ++ ;
            if (count == 3) {
                break;
            }};
        }
    }


}
