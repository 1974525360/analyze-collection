package com.zms.test;

import com.zms.collection.HashNode;
import com.zms.collection.domain.Node;

import java.util.HashMap;

/**
 * @Description : TODO
 * @author: zeng.maosen
 * @date: 2022/7/28
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        HashNode<String, User> nodes = new HashNode<>();
        int minSize = 2000000;
        HashMap<String, User> map = new HashMap<>();
//        nodes.put("1",2L);
//        nodes.put("2",3L);
////        System.out.println(nodes.getSize());
////        System.out.println(nodes.getValue("1"));
//        for (Node<String, Long> node : nodes.getSetNode()) {
//            System.out.println(node.getKey());
//            System.out.println(node.getValue());
//        }
//        System.out.println();
//        nodes.remove("2");
        User[] users = new User[minSize];
        String[] s = new String[minSize];
        for (int i = 0; i < minSize; i++) {
            users[i] = new User(i+"",i+"",i+"",i);
            s[i] = i+"";
        }
        Long starta = System.currentTimeMillis();
        for (int i = 0; i < minSize; i++) {
            nodes.put(s[i], users[i]);
//            nodes.put(i,users[i]);
        }
        Long enda = System.currentTimeMillis();
        System.out.println(enda - starta);
        Long startb = System.currentTimeMillis();
        for (int i = 0; i < minSize; i++) {
            map.put(s[i], users[i]);
//            map.put(i,users[i]);
        }
        Long endb = System.currentTimeMillis();
        System.out.println(endb - startb);


        Long startc = System.currentTimeMillis();
        nodes.getSetNode();
//        nodes.remove("1029");
        Long endc = System.currentTimeMillis();
        System.out.println(endc - startc);


//        HashSet<String> strings = new HashSet<>();
////        nodes.put("21",21);
//        System.out.println(nodes.getSize());
//        for (Node<String, Integer> node : nodes.getSetNode()) {
//            strings.add(node.getKey());
//        }
//        nodes.remove(s[minSize-minSize]);
//        nodes.put(s[minSize-1],users[minSize-1]);
//        for (Node<String, User> node : nodes.getSetNode()) {
//            System.out.println(node.getKey());
//            System.out.println(node.getValue());
//        }
//        System.out.println(strings.size());
//

        Node<String, User>[] setNode = nodes.getSetNode();
        System.out.println();
//        System.out.println(Objects.equals("7","21"));
    }
}
