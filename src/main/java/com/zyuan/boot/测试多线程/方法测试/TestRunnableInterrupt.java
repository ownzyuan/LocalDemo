package com.zyuan.boot.测试多线程.方法测试;

class ImplementRunnableInterrupt implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "线程正常执行了");
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "线程被中断了");
            e.printStackTrace();
        }
    }
}

// interrupt：当一个线程调用了sleep、wait、join方法后，这个线程处于阻塞状态，
// 此时再调用interrupt方法会立即打破这种阻塞状态，并抛出InterruptedException异常
public class TestRunnableInterrupt {

    public static void main(String[] args) {
        ImplementRunnableInterrupt runInterrupt = new ImplementRunnableInterrupt();
        Thread threadA = new Thread(runInterrupt, "A");
        threadA.start();
        threadA.setPriority(5);
        try {
            Thread.sleep(2000);
            System.out.println("执行线程中断");
            threadA.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
