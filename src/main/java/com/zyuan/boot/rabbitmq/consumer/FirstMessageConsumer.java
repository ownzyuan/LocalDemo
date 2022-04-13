package com.zyuan.boot.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.zyuan.boot.rabbitmq.message.constant.MqConstant;
import com.zyuan.boot.rabbitmq.message.dto.FirstMessageDTO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FirstMessageConsumer {

    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(value = MqConstant.FIRST_MESSAGE_QUEUE),
                            exchange = @Exchange(value = MqConstant.FIRST_MESSAGE_EXCHANGE),
                            key = MqConstant.FIRST_MESSAGE_KEY
                    )
            }
    )
    public void consumeFirstMessage(@Payload FirstMessageDTO firstMessageDTO, Channel channel, Message message) {
        System.out.println("消费到了第一条信息");
        try {
            System.out.println(firstMessageDTO.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
