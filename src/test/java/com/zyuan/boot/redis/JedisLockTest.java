package com.zyuan.boot.redis;

import com.zyuan.boot.redis.测试锁.JedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JedisLockTest {

    @Autowired
    private JedisLock jedisLock;

    public static void main(String[] args) {
        // 测试setNx加锁的方式
//        FutureTask<String> futureTaskA = new FutureTask<>(new CallableTestSetNx());
//        FutureTask<String> futureTaskB = new FutureTask<>(new CallableTestSetNx());
        // 测试set加锁的方式
        FutureTask<String> futureTaskA = new FutureTask<>(new CallableTestSet());
        FutureTask<String> futureTaskB = new FutureTask<>(new CallableTestSet());
        Thread threadA = new Thread(futureTaskA, "A线程");
        Thread threadB = new Thread(futureTaskB, "B线程");
        threadA.start();
        threadB.start();
        try {
            System.out.println(futureTaskA.get());
            System.out.println(futureTaskB.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

class CallableTestSetNx implements Callable<String> {
    @Override
    public String call() throws Exception {
        String result = Thread.currentThread().getName() + "执行" + "_";
        JedisLock jedisLock = new JedisLock();
        Long num = jedisLock.testSetNx();
        return result + num;
    }
}

class CallableTestSet implements Callable<String> {
    @Override
    public String call() throws Exception {
        String result = Thread.currentThread().getName() + "执行" + "_";
        long id = Thread.currentThread().getId();
        JedisLock jedisLock = new JedisLock();
        String s = jedisLock.testSet(id);
        return result + id;
    }
}