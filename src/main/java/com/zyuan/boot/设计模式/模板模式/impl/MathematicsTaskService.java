package com.zyuan.boot.设计模式.模板模式.impl;

import com.zyuan.boot.设计模式.模板模式.LearningTasksService;
import org.springframework.stereotype.Service;

@Service("MathematicsTask")
public class MathematicsTaskService extends LearningTasksService {

    @Override
    protected void doExaminationPaper() {
        System.out.println("做数学试卷");
    }

    @Override
    protected void doRecitationTask() {
        System.out.println("背公式");
    }

}
