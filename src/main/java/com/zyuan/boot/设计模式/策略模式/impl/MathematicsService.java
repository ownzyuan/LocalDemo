package com.zyuan.boot.设计模式.策略模式.impl;

import com.zyuan.boot.设计模式.策略模式.ILearningService;
import org.springframework.stereotype.Service;

// 数学学习
@Service("Mathematics")
public class MathematicsService implements ILearningService {

    @Override
    public void startLearning() {
        System.out.println("背公式");
        System.out.println("做一张试卷");
        System.out.println("总结做题经历");
    }

}
