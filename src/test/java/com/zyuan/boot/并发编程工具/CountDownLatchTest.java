package com.zyuan.boot.并发编程工具;

import com.alibaba.fastjson.JSONObject;
import com.zyuan.boot.entity.EventOrder;
import com.zyuan.boot.mapper.EventOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CountDownLatchTest {

    @Autowired
    private EventOrderMapper eventOrderMapper;

    @Test
    public void selectById() {
        // 模拟5个并发请求
        CountDownLatch latch = new CountDownLatch(5);
        for (Integer i = 1; i <= 5; i++) {
            Long id = i.longValue();
            new Thread(() -> {
                try {
                    // 先阻塞改线程
                    latch.await();
                    EventOrder eventOrder = eventOrderMapper.selectById(id);
                    String eventStr = JSONObject.toJSONString(eventOrder);
                    System.out.println(eventStr);
                    System.out.println("===========");
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(10);
                // 让计数器-1，直至为0，唤醒所有被CountDownLatch阻塞的线程
                latch.countDown();
                System.out.println("countDownLatch执行一次，剩余count：" + latch.getCount());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}