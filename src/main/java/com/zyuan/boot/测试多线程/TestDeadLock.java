package com.zyuan.boot.测试多线程;

// 测试死锁
public class TestDeadLock {

    // 资源A
    private static Object resourceA = new Object();
    // 资源B
    private static Object resourceB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始等待线程B");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + "获取到资源B");
                }
            }
        }, "线程A");
        Thread threadB = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + "开始执行");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始等待线程A");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + "获取到资源A");
                }
            }
        }, "线程B");
        threadA.start();
        threadB.start();
//        new Thread(() -> {
//            synchronized (resourceA) {
//                System.out.println(Thread.currentThread().getName() + "开始执行");
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName() + "开始等待线程B");
//                synchronized (resourceB) {
//                    System.out.println(Thread.currentThread().getName() + "获取到资源B");
//                }
//            }
//        }, "线程B").start();
    }
    // 结果：
    // 线程A开始执行
    // 线程B开始执行
    // 线程A开始等待线程B
    // 线程B开始等待线程A
}
