package com.zyuan.boot.Java封装类.集合;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class TestQueue {

    public static void main(String[] args) {
        Queue<String> linkedQueue = new LinkedList<>();
        Queue<String> stringQueue = new LinkedBlockingDeque<>();
//        stringQueue.add(null);
//        stringQueue.offer(null);
//        stringQueue.remove();
        stringQueue.poll();
    }

}
