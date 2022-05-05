package com.zyuan.boot.设计模式.策略模式;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ILearningServiceTest {

    @Autowired
    private Map<String, ILearningService> learningServiceMap;

    @Test
    public void startLearning() {
        // 语文学习
        learningServiceMap.get("Chinese").startLearning();
        // 数学学习
//        learningTasksServiceMap.get("Mathematics").startLearning();
        // 英语学习
//        learningTasksServiceMap.get("English").startLearning();
    }

}