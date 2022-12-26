package com.zyuan.boot.Java封装类.集合;

import com.alibaba.fastjson.JSONArray;

import java.util.*;

public class TestFailFast {

    public static void main(String[] args) {
        testFailFastBySingleThread();
//        testFailFastByMultiThread();
    }

    // 单线程测试快速失败机制
    private static void testFailFastBySingleThread() {
        HashMap<String,String> hashMap =new LinkedHashMap<>();
        hashMap.put("1","a");
        hashMap.put("2","b");
        hashMap.put("3","c");
        Iterator<Map.Entry<String,String>> iterator=hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            hashMap.put("4","d");
            System.out.println(iterator.next());
        }
    }

    // 多线程测试快速失败机制
    private static void testFailFastByMultiThread() {
        List<String> list = new ArrayList();
        list.add("基础元素");
        String listStr = JSONArray.toJSONString(list);
        System.out.println("主线程list集合：" + listStr);
        Thread threadOne = new Thread(new AddRunnable(list), "线程一");
        Thread threadTwo = new Thread(new RemoveRunnable(list), "线程二");
        threadOne.start();
        threadTwo.start();
    }

}

class AddRunnable implements Runnable {
    private List<String> list;

    public AddRunnable(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println("线程一开始");
        for (int i = 0; i < 10; i++) {
            list.add(i + "：线程一");
        }
        String listOne = JSONArray.toJSONString(list);
        System.out.println("线程一list集合：" + listOne);
    }
}

class RemoveRunnable implements Runnable {
    private List<String> list;

    public RemoveRunnable(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        System.out.println("线程二开始");
        while (list.iterator().hasNext()) {
            String str = list.iterator().next();
            list.remove(str);
        }
        String listOne = JSONArray.toJSONString(list);
        System.out.println("线程二list集合：" + listOne);
    }
}
