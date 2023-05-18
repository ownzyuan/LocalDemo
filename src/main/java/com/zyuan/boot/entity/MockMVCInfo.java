package com.zyuan.boot.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class MockMVCInfo implements Serializable {

    private static final Long serializableID = -1L;

    private Long id;

    private String name;

}
