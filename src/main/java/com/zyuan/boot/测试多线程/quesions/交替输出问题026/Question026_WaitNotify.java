package com.zyuan.boot.测试多线程.quesions.交替输出问题026;

import java.util.concurrent.CountDownLatch;

/**
 * 有一个纯数字数组和纯字母数组，要求交替输出数字、字母
 * 使用wait、notify实现
 */
public class Question026_WaitNotify {

    static Thread a;
    static Thread b;
    // 通过构造方法设置值，值减到0时，会唤醒所有等待在这个latch上的线程
    static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9};
        char[] chars = "abcde".toCharArray();

        Object obj = new Object();
        /**
         * 这样写有一个问题，不能保证输出的先后顺序，如果要求先数字后字母，那么这个就不能做到
         * 因此需要使用CountDownLatch来控制先后顺序
         */
        a = new Thread(() -> {
            synchronized (obj) {
                for (int num : nums) {
                    System.out.print(num);
                    // latch的值-1，如果减到0，则唤醒所有等待在这个latch上的线程
                    latch.countDown();
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                obj.notify();
            }
        }, "nameA");

        b = new Thread(() -> {
            try {
                // 线程进入同步等待，直至latch的值为0或者线程被中断时，此线程被唤醒
                latch.await();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            synchronized (obj) {
                for (char aChar : chars) {
                    System.out.print(aChar);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
                obj.notify();
            }
        }, "nameB");

        a.start();
        b.start();
    }

}
