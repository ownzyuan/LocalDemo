package com.zyuan.boot.controller;

import com.zyuan.boot.MySQL死锁.ITestLockService;
import com.zyuan.boot.entity.TestLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test/lock")
public class TestLockController {

    @Autowired
    private ITestLockService testLockService;

    @GetMapping("/list")
    public List<TestLock> selectAllList() {
        return testLockService.selectAllList();
    }

    @PostMapping("/batchInsert")
    public String batchInsert(@RequestBody List<TestLock> testLocks) {
        String result;
        try {
            testLockService.batchInsert(testLocks);
            result = "新增成功";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = "新增失败";
        }
        return result;
    }

    @PostMapping("/batchInsert/byMap")
    public String batchInsertByMap(@RequestBody Map<String,String> insertMap) {
        String result;
        try {
            testLockService.batchInsertByMap(insertMap);
            result = "新增成功";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = "新增失败";
        }
        return result;
    }
}
