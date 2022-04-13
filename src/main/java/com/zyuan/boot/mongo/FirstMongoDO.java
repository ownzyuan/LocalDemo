package com.zyuan.boot.mongo;

import lombok.Data;

import java.util.Date;

@Data
public class FirstMongoDO {

    private String firstName;

    private Integer firstType;

    private String firstTitle;

    private Date createTime;

    private Long createId;
}
