package com.zyuan.boot.MySQL死锁;

import com.zyuan.boot.entity.TestLock;

import java.util.List;

public interface ITestLockService {

    List<TestLock> selectAllList();

    void batchInsert(List<TestLock> testLocks);

}
