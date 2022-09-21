package com.zyuan.boot.测试多线程.quesions.交替输出问题026;

import java.util.concurrent.locks.LockSupport;

/**
 * 有一个纯数字数组和纯字母数组，要求交替输出数字、字母
 * 使用LockSupport实现
 */
public class Question026_LockSupport {

    static Thread a;
    static Thread b;

    public static void main(String[] args) {
        int[] nums = {1,3,5,7,9};
        char[] chars = "abcde".toCharArray();

        a = new Thread(() -> {
            for (int num : nums) {
                System.out.print(num);
                // 输出后叫醒b线程
                LockSupport.unpark(b);
                // 然后进入阻塞，等待b唤醒
                LockSupport.park();
            }
        });

        b = new Thread(() -> {
            for (char aChar : chars) {
                // 先进入阻塞状态，等待a唤醒，如果不先阻塞，那么也会先执行
                LockSupport.park();
                // 当a唤醒后，就输出
                System.out.print(aChar);
                // 输出完毕唤醒a
                LockSupport.unpark(a);
            }
        });

        a.start();
        b.start();
    }

}
