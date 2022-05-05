package com.zyuan.boot.设计模式.模板模式;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ILearningTasksServiceTest {

    @Autowired
    private Map<String,ILearningTasksService> learningTasksServiceMap;

    @Test
    public void doLearningTasks() {
        String subject = "MathematicsTask";
        learningTasksServiceMap.get(subject).doLearningTasks();
    }

}