package com.fanlm.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: fintechRiskMonitoringPlatform
 * @description: LRU 最近最少
 * @author: flm
 * @create: 2020-12-07 13:50
 **/
public class LRUCache {
    //最大容量
    int capaCity;
    //内弄 map
    Map<Integer,Node> map = new HashMap<>();
    //头部节点
    Node head = null;
    //尾部节点
    Node end = null;

    public LRUCache(int capaCity){
        this.capaCity = capaCity;
    }

    public int get(int key){
        if (map.containsKey(key)){
            Node n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }else{
            return -1;
        }
    }

    public void remove(Node node){
        if (node.pre != null){
            node.pre.next = node.next;
        }else {
            head = node.next;
        }
        if (node.next != null){
            node.next.pre = node.pre;
        }else {
            end = node.pre;
        }
    }
    public void setHead (Node node){
        node.next = head;
        node.pre = null;
        if (head != null){
            head.pre = node;
            head = node;
        }
        //防止只有一个元素
        if (end == null){
            end = head;
        }
    }

    public void set(int key , int value){
        if (map.containsKey(key)){
            Node n = map.get(key);
            n.value = value;
            remove(n);
            setHead(n);
        }else {
            Node node = new Node(key, value);
            if (map.size() >= capaCity){
                map.remove(end.key);
                remove(end);
                setHead(node);
            }else {
                setHead(node);
            }
            map.put(key,node);
        }
    }

}


class Node{
    int key ;
    int value;
    Node pre;
    Node next;

    public Node(int key ,int value){
        this.key = key;
        this.value = value;
    }
}
