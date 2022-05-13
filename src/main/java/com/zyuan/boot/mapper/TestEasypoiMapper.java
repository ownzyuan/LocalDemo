package com.zyuan.boot.mapper;

import com.zyuan.boot.entity.TestEasypoi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestEasypoiMapper {

    List<TestEasypoi> selectAllData();

    void updateTestEasyPoi(@Param("testEasypoi") TestEasypoi testEasypoi);
}
