package com.zyuan.boot.redis;

import com.zyuan.boot.redis.测试消息队列.MessageProducer;
import com.zyuan.boot.redis.测试消息队列.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String messageKey = "messageKey";

    @Test
    public void testPingRedis() {
        String key = "kkkkkkey";
        String value = "vvvvvvalue";
        Boolean hasKey = false;
        System.out.println(hasKey);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, 60, TimeUnit.SECONDS);
        Duration duration = Duration.ZERO;
        hasKey = redisTemplate.hasKey(key);
        System.out.println(hasKey);
        // 设置过期时间为0时，该key立刻被清除了
        Boolean expire = redisTemplate.expire(key, duration);
        System.out.println(expire);
        hasKey = redisTemplate.hasKey(key);
        System.out.println(hasKey);
    }

    @Test
    public void testList() {
        String value1 = "aaa";
        String value2 = "bbb";
        String value3 = "ccc";
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(messageKey, value1);
        listOperations.leftPush(messageKey, value2);
        listOperations.leftPush(messageKey, value3);
    }

    @Test
    public void testCreateMessage() {
        Long size = messageService.createMessage();
    }

}