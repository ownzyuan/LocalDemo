package com.zyuan.boot.测试多线程;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class ImplementCallableInt implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "执行中");
        return 100;
    }
}

class ImplementCallableString implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "执行中");
        return "abc";
    }
}

public class TestCallable {

    public static void main(String[] args) {
//        FutureTask<Integer> futureTask = new FutureTask<>(new ImplementCallableInt());
        FutureTask<String> futureTask = new FutureTask<>(new ImplementCallableString());
        Thread thread = new Thread(futureTask,"callable线程");
        thread.start();
        try {
            System.out.println("结果为" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
