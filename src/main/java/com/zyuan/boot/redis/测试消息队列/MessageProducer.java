package com.zyuan.boot.redis.测试消息队列;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Service
public class MessageProducer implements Callable<String> {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private MessageService messageService;

    private static final String messageKey = "messageKey";
    private volatile Integer count = 1;

    private Long createMessage(String message) {
        Long size = messageService.createMessage(message);
        count ++;
        return size;
    }

    @Override
    public String call() throws Exception {
        Long size = 0L;
        for (int i = 0; i < 5; i++) {
            size = createMessage("message" + count);
        }
        return Thread.currentThread().getName() + "messageSize:" + size + ", count:" + count;
    }

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(new MessageProducer());
        Thread thread01 = new Thread(futureTask, "thread01");
//        Thread thread02 = new Thread(futureTask, "thread02");
        thread01.start();
//        thread02.start();
    }

}
