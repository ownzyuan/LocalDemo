package com.zyuan.boot.测试多线程;

class ExtendThread extends Thread {
    private String name;

    public ExtendThread(String name) {
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "运行" + i);
        }
    }
}

public class TestThread {
    public static void main(String[] args) {
        ExtendThread testC = new ExtendThread("线程C");
        ExtendThread testD = new ExtendThread("线程D");
        Thread threadC = new Thread(testC);
        Thread threadD = new Thread(testD);
        threadC.start();
        threadD.start();
    }
}
