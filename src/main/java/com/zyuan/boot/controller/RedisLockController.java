package com.zyuan.boot.controller;

import com.zyuan.boot.redis.测试锁.JedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis/lock")
public class RedisLockController {

    @Autowired
    private JedisLock jedisLock;

    @GetMapping("/setNx")
    public String testSetNx() {
        String result = null;
        try {
            Long num = jedisLock.testSetNx();
            Thread.sleep(1000);
            if (num == 1L) {
                result = "执行成功:" + num;
            } else {
                result = "执行失败:" + num;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "执行失败";
        }
        return result;
    }

    @GetMapping("/set")
    public String testSet() {
        String result = null;
        try {
            long id = Thread.currentThread().getId();
            String s = jedisLock.testSet(id);
            Thread.sleep(2000);
            result = "执行成功:" + s + "_" + id;
        } catch (Exception e) {
            e.printStackTrace();
            result = "执行失败";
        }
        return result;
    }

    @GetMapping("/spin")
    public String testSpinLock() {
        String result = null;
        try {
            long id = Thread.currentThread().getId();
            String s = jedisLock.spinLock(id);
            result = "执行成功:" + s + "_" + id;
        } catch (Exception e) {
            e.printStackTrace();
            result = "执行失败";
        }
        return result;
    }

}
