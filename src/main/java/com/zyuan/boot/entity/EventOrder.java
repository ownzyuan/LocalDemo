package com.zyuan.boot.entity;

import lombok.Data;

@Data
public class EventOrder {

    private Long id;

    private String eventName;

    private Integer eventType;

    private String orderNo;

}
