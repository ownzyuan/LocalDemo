package com.zyuan.boot.设计模式.模板模式;

import org.springframework.stereotype.Service;

@Service
public abstract class LearningTasksService implements ILearningTasksService {

    // 定义做试卷的方法，由子类实现
    protected abstract void doExaminationPaper();
    // 定义背诵的方法，由子类实现
    protected abstract void doRecitationTask();

    @Override
    public void doLearningTasks() {
        System.out.println("打开课本");
        doExaminationPaper();
        doRecitationTask();
        System.out.println("放松一下");
    }
}
