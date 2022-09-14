package com.zyuan.boot.redission;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestRLock {

    @Autowired
    private RedissonClient redissonClient;

    public void testRLock() {
        RLock lock = redissonClient.getLock("123");
        try {
            lock.lockAsync();
            lock.tryLock(10, 3, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } finally {
            lock.lock();
        }
    }

}
