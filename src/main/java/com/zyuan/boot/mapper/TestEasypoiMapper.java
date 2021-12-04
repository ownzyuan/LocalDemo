package com.zyuan.boot.mapper;

import com.zyuan.boot.entity.TestEasypoi;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestEasypoiMapper {

    List<TestEasypoi> selectAllData();

}
