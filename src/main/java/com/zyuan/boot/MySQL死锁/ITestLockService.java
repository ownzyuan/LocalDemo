package com.zyuan.boot.MySQL死锁;

import com.zyuan.boot.entity.TestLock;

import java.util.List;
import java.util.Map;

public interface ITestLockService {

    List<TestLock> selectAllList();

    void batchInsert(List<TestLock> testLocks);

    void batchInsertByMap(Map<String,String> insertMap);
}
