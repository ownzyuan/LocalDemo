package com.zyuan.boot.easypoi;

import com.zyuan.boot.entity.TestEasypoi;

import java.util.List;

public interface ITestExportByEasypoi {

    void testExport();

    List<TestEasypoi> testTransactional();
}
