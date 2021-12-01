package com.zyuan.boot.测试异步;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
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

        while (true) {
            if (taskA.isDone() && taskB.isDone() && taskC.isDone()) {
                break;
            }
            Thread.sleep(10000);
        }
        long end = new Date().getTime();
        System.out.println("任务结束，当前时间：" + end);
        System.out.println("总任务耗时：" + (end - star) + "ms");
    }

}