package com.zyuan.boot.rabbitmq.producer;

import com.zyuan.boot.rabbitmq.message.constant.MqConstant;
import com.zyuan.boot.rabbitmq.message.dto.FirstMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FirstMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendFirstMessage(FirstMessageDTO firstMessageDTO) {
        System.out.println("发送第一条消息了");
        try {
            rabbitTemplate.convertAndSend(MqConstant.FIRST_MESSAGE_EXCHANGE, MqConstant.FIRST_MESSAGE_KEY, firstMessageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
