package com.zyuan.boot.MySQL死锁;

import com.zyuan.boot.entity.TestLock;
import com.zyuan.boot.mapper.TestLockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestLockServiceImpl implements ITestLockService{

    @Autowired
    private TestLockMapper testLockMapper;

    @Override
    public List<TestLock> selectAllList() {
        return testLockMapper.selectAllList();
    }

    @Override
    public void batchInsert(List<TestLock> testLocks) {
        List<TestLock> testLockList = selectAllList();
        testLockList.addAll(testLocks);
        testLockMapper.batchInsert(testLockList);
    }
}
