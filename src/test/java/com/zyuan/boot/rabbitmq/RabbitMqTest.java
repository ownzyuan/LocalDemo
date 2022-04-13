package com.zyuan.boot.rabbitmq;

import com.zyuan.boot.rabbitmq.message.dto.FirstMessageDTO;
import com.zyuan.boot.rabbitmq.producer.FirstMessageProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitMqTest {

    @Autowired
    private FirstMessageProducer firstMessageProducer;

    @Test
    public void sendFirstMessage() {
        FirstMessageDTO firstMessageDTO = new FirstMessageDTO();
        firstMessageDTO.setId(1111L);
        firstMessageDTO.setMessageInfo("这是第一条消息");
        firstMessageProducer.sendFirstMessage(firstMessageDTO);
    }
}
