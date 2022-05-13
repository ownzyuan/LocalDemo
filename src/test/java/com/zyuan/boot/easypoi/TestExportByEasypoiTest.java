package com.zyuan.boot.easypoi;

import com.zyuan.boot.entity.TestEasypoi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestExportByEasypoiTest {

    @Autowired
    private ITestExportByEasypoi testExportByEasypoi;

    @Test
    public void testExport() {
        testExportByEasypoi.testExport();
    }

    @Test
    public void testTransactional() {
        List<TestEasypoi> testEasypois = testExportByEasypoi.testTransactional();
    }
}