package com.zyuan.boot.测试多线程;

// 测试线程运行
class ImplementRunnable01 implements Runnable{

    private String name;

    public ImplementRunnable01(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "运行" + i);
        }
    }
}

// 测试join方法
class ImplementRunnable02 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}

// 测试sleep
class ImplementRunnable03 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + i);
        }
    }
}

// 测试interrupt(线程中断)
class ImplementRunnable04 implements Runnable{
    @Override
    public void run() {
        System.out.println("进入run方法开始休眠");
        try {
            Thread.sleep(1000);
            System.out.println("休眠1秒完成");
        } catch (InterruptedException e) {
            System.out.println("休眠被终止了");
            return;
        }
        System.out.println("程序正常结束");
    }
}

public class TestRunnable {
    // 测试线程运行
    /*
    public static void main(String[] args) {
        ImplementRunnable01 testA = new ImplementRunnable01("线程A");
        ImplementRunnable01 testB = new ImplementRunnable01("线程B");
        // 构造方法在执行时，就将Runnable对象设置为传参的对象
        Thread threadA = new Thread(testA);
        Thread threadB = new Thread(testB);
        // Thread其实也是实现了Runnable接口，他的start方法就是调用了run方法，
        // 而testA对象是实现了run方法，因此调用的run方法是testA的
        threadA.start();
        threadB.start();
    }
    */

    // 测试join方法
    /*
    public static void main(String[] args) {
        ImplementRunnable02 testRun = new ImplementRunnable02();
        Thread t1 = new Thread(testRun,"线程执行");
        t1.start();
        for (int i = 0; i < 3; i++) {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("join进来的线程执行" + i);
        }
    }
    */

    // 测试sleep
    /*
    public static void main(String[] args) {
        ImplementRunnable03 testSleep = new ImplementRunnable03();
        Thread t1 = new Thread(testSleep, "线程执行");
        t1.start();
        for (int i = 0; i < 3; i++) {
            System.out.println("非线程执行"+i);
        }
    }
     */

    public static void main(String[] args) {
        ImplementRunnable04 testInterrupt = new ImplementRunnable04();
        Thread t1 = new Thread(testInterrupt);
        t1.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
    }
}
