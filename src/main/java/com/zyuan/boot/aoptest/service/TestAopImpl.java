package com.zyuan.boot.aoptest.service;

import com.zyuan.boot.aoptest.aop.TestAopConfig;
import org.springframework.stereotype.Service;

@Service
public class TestAopImpl implements ITestAop {

    @Override
    @TestAopConfig
    public void printInfo() {
        System.out.println("test:print");
    }

    @Override
    public void paramMethod(String args) {
        System.out.println("param:" + args);
    }

    @Override
    public void anyParamMethod(String str1, String str2) {
        System.out.println(str1 + "+" + str2);
    }

    @Override
    public void anyParamMethod02(String str1, String str2, String str3) {
        System.out.println(str1 + "+" + str2 + "+" + str3);
    }

}
