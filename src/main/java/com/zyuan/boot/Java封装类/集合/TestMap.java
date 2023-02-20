package com.zyuan.boot.Java封装类.集合;

import java.util.HashMap;
import java.util.Map;

public class TestMap {

    public static void main(String[] args) {
        Map<String,String> hashMap = new HashMap<>();
        for (int i = 1; i <= 20; i++) {
            String key = "key" + i;
            String value = "value" + i;
            hashMap.put(key, value);
        }
        hashMap.put("key10", "value001");
    }

}
