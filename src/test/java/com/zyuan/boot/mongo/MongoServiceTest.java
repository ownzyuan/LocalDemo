package com.zyuan.boot.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoServiceTest {

    @Autowired
    private IMongoService mongoService;

    @Test
    public void testInsert() {
        mongoService.insertIntoMongo();
    }

    @Test
    public void testSelectCount() {
        long start = System.currentTimeMillis();
        mongoService.selectCount();
        long end = System.currentTimeMillis();
        System.out.println("执行：" + (end - start) + "ms");
    }
}