package com.zyuan.boot.rabbitmq.message.constant;

/**
 * 存放mq的常量
 * 包括 队列，交换机，key
 */
public class MqConstant {

    // 队列：存储消息的容器，用来保存消息，直到消息发送给消费者
    public static final String FIRST_MESSAGE_QUEUE = "firstMessageQueue";
    // 交换机：提供Producer到Queue之间的匹配，接收生产者发送的消息并将这些消息按照路由规则转发到消息队列，
    // 不会存储消息 ，如果没有 Queue绑定到 Exchange 的话，它会直接丢弃掉 Producer 发送过来的消息
    public static final String FIRST_MESSAGE_EXCHANGE = "firstMessageExchange";
    // 路由键：消息头的一个属性，用于标记消息的路由规则，决定了交换机的转发路径
    public static final String FIRST_MESSAGE_KEY = "firstMessageKey";

    // 队列：存储消息的容器，用来保存消息，直到消息发送给消费者
    public static final String THE_MESSAGE_QUEUE = "theMessageQueue";
    // 交换机：提供Producer到Queue之间的匹配，接收生产者发送的消息并将这些消息按照路由规则转发到消息队列，
    // 不会存储消息 ，如果没有 Queue绑定到 Exchange 的话，它会直接丢弃掉 Producer 发送过来的消息
    public static final String THE_MESSAGE_EXCHANGE = "theMessageExchange";
    // 路由键：消息头的一个属性，用于标记消息的路由规则，决定了交换机的转发路径
    public static final String THE_MESSAGE_KEY = "theMessageKey";

}
