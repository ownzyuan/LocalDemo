package com.zyuan.boot.redis.测试消息队列;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Service
public class MessageService{

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String messageKey = "messageKey";

    public Long createMessage() {
        Long size = 0L;
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        for (int i = 0; i < 5; i++) {
            size = listOperations.leftPush(messageKey, "message" + i);
        }
        System.out.println(size);
        return size;
    }

    public Long createMessage(String message) {
        Long size = 0L;
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        try {
            size  = listOperations.leftPush(messageKey, message);
        } catch (NullPointerException e) {
            System.out.println("空指针了");
        }
        System.out.println(size);
        return size;
    }

//    public static void main(String[] args) {
//        ListOperations<String, String> listOperations = redisTemplate.opsForList();
//        Long size = listOperations.leftPush(messageKey, "ggg");
//        System.out.println(size);
//    }

}
