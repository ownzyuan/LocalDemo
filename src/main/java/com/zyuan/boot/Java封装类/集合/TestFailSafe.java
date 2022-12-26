package com.zyuan.boot.Java封装类.集合;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestFailSafe {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("1","a");
        concurrentHashMap.put("2","b");
        concurrentHashMap.put("3","c");
        Iterator<Map.Entry<String, String>> iterator = concurrentHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            concurrentHashMap.put("4", "d");
            System.out.println(iterator.next());
        }
    }

}
