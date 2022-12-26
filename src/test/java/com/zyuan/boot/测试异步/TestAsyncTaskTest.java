package com.zyuan.boot.测试异步;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAsyncTaskTest {

    @Autowired
    private TestAsyncTask testAsyncTask;

    @Test
    public void testAsyncTask() throws InterruptedException {
        long star = new Date().getTime();
        System.out.println("任务开始，当前时间：" + star);
        Future<String> taskA = testAsyncTask.taskA();
        Future<String> taskB = testAsyncTask.taskB();
        Future<String> taskC = testAsyncTask.taskC();
        CompletableFuture<Void> taskD = CompletableFuture.runAsync(() -> {
            System.out.println("D开始");
            long starD = new Date().getTime();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = new Date().getTime();
            System.out.println("D耗时：" + (end - starD) + "ms");
        });
        System.out.println("main流程继续执行");
        while (true) {
            if (taskA.isDone() && taskB.isDone() && taskC.isDone() && taskD.isDone()) {
                break;
            }
            Thread.sleep(1000);
        }
        long end = new Date().getTime();
        System.out.println("任务结束，当前时间：" + end);
        System.out.println("总任务耗时：" + (end - star) + "ms");
    }

}