package com.zyuan.boot.rabbitmq.message.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TheMessageDTO implements Serializable {

    private Long id;

    private String messageInfo;

}
