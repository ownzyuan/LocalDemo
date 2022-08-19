package com.zyuan.boot.mapper;

import com.zyuan.boot.entity.TestLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestLockMapper {

    List<TestLock> selectAllList();

    void batchInsert(@Param("testLocks") List<TestLock> testLocks);

}
