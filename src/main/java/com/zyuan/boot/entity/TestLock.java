package com.zyuan.boot.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestLock implements Serializable {

    private static final Long serializableID = -1L;

    private Long id;

    private Integer code;

    private Integer other;

    private Long unionId;

}
