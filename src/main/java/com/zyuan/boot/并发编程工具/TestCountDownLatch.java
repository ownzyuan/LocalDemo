package com.zyuan.boot.并发编程工具;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    public static void main(String[] args) {
        // 模拟并发
//        simulateConcurrent();
        // 异步任务执行完成之后，再回到主线程任务
        simulateSync();
    }

    /**
     * 模拟并发
     * 通过latch的await方法阻塞需要执行的10个线程，然后再通过countDown逐个减少数量，直至为0，
     * 这时10个线程都将被唤醒，同时执行任务
     */
    private static void simulateConcurrent() {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    System.out.println(Thread.currentThread().getName() + "开始运行");
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(10);
                latch.countDown();
                System.out.println("countDownLatch执行一次，剩余count：" + latch.getCount());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    /**
     * 异步任务执行完成之后，再回到主线程任务
     */
    private static void simulateSync() {
        CountDownLatch latch = new CountDownLatch(2);
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10);
                System.out.println("异步任务一执行完成");
                latch.countDown();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10);
                System.out.println("异步任务二执行完成");
                latch.countDown();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        try {
            latch.await();
            System.out.println("异步任务执行后，再执行主线程");
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

}
