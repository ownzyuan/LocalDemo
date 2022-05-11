package com.zyuan.boot.redis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThisIsDTO implements Serializable {

    private String name;

    private Integer age;

    private Long time;

    private Long iiid;

}
