package com.zyuan.boot.rabbitmq.producer;

import com.zyuan.boot.rabbitmq.message.constant.MqConstant;
import com.zyuan.boot.rabbitmq.message.dto.TheMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(TheMessageDTO theMessageDTO) {
        System.out.println("发送了一条消息");
        try {
            rabbitTemplate.convertAndSend(MqConstant.THE_MESSAGE_EXCHANGE, MqConstant.THE_MESSAGE_KEY, theMessageDTO);
            System.out.println("消息发送成功");
        } catch (Exception e) {
            System.out.println("消息发送失败");
            e.printStackTrace();
        }
    }

}
