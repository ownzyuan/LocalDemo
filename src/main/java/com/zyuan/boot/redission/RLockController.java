package com.zyuan.boot.redission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rlock")
public class RLockController {

    @Autowired
    private TestRLock testRLock;

    @GetMapping("/testRLock")
    public String testRLock() {
        testRLock.testRLock();
        return "123";
    }

}
