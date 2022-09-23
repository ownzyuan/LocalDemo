package com.zyuan.boot.spring.测试循环引用;

import com.zyuan.boot.MySQL死锁.ITestLockService;
import com.zyuan.boot.redis.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BService {

    @Autowired
    private ITestLockService testLockService;

    @Autowired
    private AService aService;

    @Async
    public void methodB() {
        aService.methodB();
    }

}
