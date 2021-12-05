package com.zyuan.boot.aoptest;

import com.zyuan.boot.aoptest.mapper.TestAopMapper;
import com.zyuan.boot.aoptest.service.ITestAop;
import com.zyuan.boot.aoptest.service.TestPrivateMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAopTest {

    @Autowired
    private ITestAop testAop;

    @Autowired
    private TestAopMapper testAopMapper;

    @Autowired
    private TestPrivateMethod testPrivateMethod;

    @Test
    public void goTest(){
//        testAop.printInfo();
//        testAop.paramMethod("zy");
//        testAop.anyParamMethod("ab","cd");
        testAop.anyParamMethod02("12","34","56");
//        testAopMapper.printMapper();
//        testPrivateMethod.printAbc();
    }

}