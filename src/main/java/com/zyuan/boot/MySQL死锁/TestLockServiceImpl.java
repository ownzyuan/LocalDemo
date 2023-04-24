package com.zyuan.boot.MySQL死锁;

import com.zyuan.boot.entity.TestLock;
import com.zyuan.boot.mapper.TestLockMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<TestLock> testLocks) {
        if (CollectionUtils.isEmpty(testLocks)) {
            testLocks = selectAllList();
        }
        testLockMapper.batchInsert(testLocks);
    }
}
