package com.zyuan.boot.aoptest.service;

import org.springframework.stereotype.Service;

@Service
public class TestPrivateMethod {

    private void privateMethod() {
        System.out.println("private方法");
    }

    public void printAbc() {
        System.out.println("public方法");
    }

}
