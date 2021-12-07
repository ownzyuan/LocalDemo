package com.zyuan.boot.测试多线程;

public class TestVolatile {

    public static void main(String[] args) {
        Thread testRun = new Thread(()-> {
            int i = 1;
            while (i < 10) {
                try {
                    System.out.println(Thread.currentThread().getName() + i++);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"线程A");
        testRun.start();
        try {
            Thread.sleep(2000);
            // Thread中，name属性被volatile修饰，所以是线程可见的，因此当主线程休眠2s后，
            // testRun的name就被修改了，然后再次执行Thread.currentThread().getName()就发生了变化
            testRun.setName("线程变了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程A1
        // 线程A2
        // 线程A3
        // 线程变了4
        // 线程变了5
        // 线程变了6
        // 线程变了7
        // 线程变了8
        // 线程变了9
    }

}
