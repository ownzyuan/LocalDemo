package com.zyuan.boot.entity;

import lombok.Data;

@Data
public class ConvertNullFiledInfo {

    private Integer intNum;

    private Long longNum;

    private String str;

    private ConvertNullFiledInfo info;

}
