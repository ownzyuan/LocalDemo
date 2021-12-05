package com.zyuan.boot.aoptest.service;

public interface ITestAop {

    void printInfo();

    void paramMethod(String args);

    void anyParamMethod(String str1, String str2);

    void anyParamMethod02(String str1, String str2, String str3);
}
