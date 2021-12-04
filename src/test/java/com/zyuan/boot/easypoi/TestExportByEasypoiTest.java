package com.zyuan.boot.easypoi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestExportByEasypoiTest {

    @Autowired
    private ITestExportByEasypoi testExportByEasypoi;

    @Test
    public void testExport() {
        testExportByEasypoi.testExport();
    }

}