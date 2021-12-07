package com.zyuan.boot.测试多线程;

// 没有加入synchronized
class MyThread01 implements Runnable {
    private int target = 5;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行" + target--);
        }
    }
}

// 加入了synchronized
class MyThread02 implements Runnable {
    private int target = 5;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchronized (this) {
                if (target > 0) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "执行" + target--);
                }
            }
        }
    }
}

// 加入了synchronized的同步方法
class MyThread03 implements Runnable {
    private int target = 5;
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            soutMethod();
        }
    }
    private synchronized void soutMethod() {
        if (target > 0) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行" + target--);
        }
    }
}

public class TestSynchronized {
    public static void main(String[] args) {
        // 没有加入synchronized
//        noSynchronized();

        // 加入了synchronized
//        haveSynchronized();

        // 加入了synchronized的同步方法
        mySynchronizedMethod();
    }

    private static void mySynchronizedMethod() {
        MyThread03 myThread03 = new MyThread03();
        Thread threadA = new Thread(myThread03, "线程A");
        Thread threadB = new Thread(myThread03, "线程B");
        Thread threadC = new Thread(myThread03, "线程C");
        threadA.start();
        threadB.start();
        threadC.start();
        // 结果：
        // 线程A执行5
        // 线程A执行4
        // 线程A执行3
        // 线程A执行2
        // 线程A执行1
    }

    private static void haveSynchronized() {
        MyThread02 myThread02 = new MyThread02();
        Thread threadA = new Thread(myThread02, "线程A");
        Thread threadB = new Thread(myThread02, "线程B");
        Thread threadC = new Thread(myThread02, "线程C");
        threadA.start();
        threadB.start();
        threadC.start();
        // 结果：
        // 线程A执行5
        // 线程A执行4
        // 线程A执行3
        // 线程A执行2
        // 线程A执行1
    }

    private static void noSynchronized() {
        MyThread02 myThread02 = new MyThread02();
        Thread threadA = new Thread(myThread02, "线程A");
        Thread threadB = new Thread(myThread02, "线程B");
        Thread threadC = new Thread(myThread02, "线程C");
        threadA.start();
        threadB.start();
        threadC.start();
        // 结果：
        // 线程A执行5
        // 线程A执行4
        // 线程A执行3
        // 线程A执行2
        // 线程A执行1
    }
}
