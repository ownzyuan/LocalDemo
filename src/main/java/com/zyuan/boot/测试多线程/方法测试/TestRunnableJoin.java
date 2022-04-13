package com.zyuan.boot.测试多线程.方法测试;

class ImplementRunnableJoinA implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行");
    }
}

class ImplementRunnableJoinB implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "执行");
    }
}
// join：主线程等待子线程的终止。也就是在子线程调用了join()方法后面的代码，只有等到子线程结束了才能执行
// 调用后，只有执行完join这条线程的代码，才会继续正常进行主线程代码
public class TestRunnableJoin {
    public static void main(String[] args) throws InterruptedException {
        ImplementRunnableJoinA joinA = new ImplementRunnableJoinA();
        ImplementRunnableJoinB joinB = new ImplementRunnableJoinB();
        Thread threadA = new Thread(joinA, "A线程");
        Thread threadB = new Thread(joinB, "B线程");
        // 正常执行
        // 结果：main -> B -> A
//        threadA.start();
//        threadB.start();
//        System.out.println("main线程执行01");

        // 使用join
        // 结果：A -> main -> B
//        threadA.start();
//        threadA.join();
//        threadB.start();
//        System.out.println("main线程执行02");

        // 都使用join
        // 结果：B -> A -> main(因为B比A的sleep时间短才先执行)
//        threadA.start();
//        threadB.start();
//        threadA.join();
//        threadB.join();
//        System.out.println("main线程执行03");

        // 都使用join
        // 结果：A -> B -> main
        // 分析：因为在B进入就绪状态前，A执行了join，导致线程进入阻塞状态，必须A执行之后才可以进行接下来的操作（相当于单线程执行）
        threadA.start();
        threadA.join();
        threadB.start();
        threadB.join();
        System.out.println("main线程执行04");

    }
}
