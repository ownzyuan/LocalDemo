package com.zyuan.boot.MySQL死锁;


import com.zyuan.boot.entity.TestLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MySQLLockTest {

    @Autowired
    private ITestLockService testLockService;

    @Test
    public void selectAll() {
        List<TestLock> testLocks = testLockService.selectAllList();
        System.out.println(testLocks.toString());
    }

}