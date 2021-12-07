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

// 测试priority(优先级)
class ImplementRunnable05 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行" + i);
        }
    }
}

// 测试yield(礼让)
class ImplementRunnable06 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行" + i);
            if (i == 1) {
                System.out.println(Thread.currentThread().getName() + "开始礼让");
                Thread.yield();
            }
        }
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

    // 测试interrupt方法
    /*
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
     */

    // 测试priority，虽然有优先级，但是线程调度还是由cpu决定
    /*
    public static void main(String[] args) {
        Thread t1 = new Thread(new ImplementRunnable05(),"最低优先级priority");
        Thread t2 = new Thread(new ImplementRunnable05(),"中等优先级priority");
        Thread t3 = new Thread(new ImplementRunnable05(),"最高优先级priority");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
    }
     */

    // 测试yield
    public static void main(String[] args) {
        Thread threadA = new Thread(new ImplementRunnable06(), "线程A");
        Thread threadB = new Thread(new ImplementRunnable06(), "线程B");
        threadA.start();
        threadB.start();
    }
}
