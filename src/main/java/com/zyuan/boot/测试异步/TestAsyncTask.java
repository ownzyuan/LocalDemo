package com.zyuan.boot.测试异步;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Future;

@Component
public class TestAsyncTask {

    @Async
    public Future<String> taskA() throws InterruptedException {
        System.out.println("A开始");
        long star = new Date().getTime();
        Thread.sleep(5000);
        long end = new Date().getTime();
        System.out.println("A耗时：" + (end - star) + "ms");
        return new AsyncResult<>("A结束");
    }

    @Async
    public Future<String> taskB() throws InterruptedException {
        System.out.println("B开始");
        long star = new Date().getTime();
        Thread.sleep(5000);
        long end = new Date().getTime();
        System.out.println("B耗时：" + (end - star) + "ms");
        return new AsyncResult<>("B结束");
    }

    @Async
    public Future<String> taskC() throws InterruptedException {
        System.out.println("C开始");
        long star = new Date().getTime();
        Thread.sleep(5000);
        long end = new Date().getTime();
        System.out.println("C耗时：" + (end - star) + "ms");
        return new AsyncResult<>("C结束");
    }

}
