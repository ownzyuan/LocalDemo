package com.zyuan.boot.spring.测试循环引用;

import com.zyuan.boot.MySQL死锁.ITestLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AService {

    @Autowired
    private ITestLockService testLockService;

    @Autowired
    private BService bService;

    public void methodB() {
        bService.methodB();
    }

    public void methodC() {

    }

}
