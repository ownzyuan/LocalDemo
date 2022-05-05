package com.zyuan.boot.设计模式.策略模式.impl;

import com.zyuan.boot.设计模式.策略模式.ILearningService;
import org.springframework.stereotype.Service;

// 语文学习
@Service("Chinese")
public class ChineseService implements ILearningService {

    @Override
    public void startLearning() {
        System.out.println("背诵古诗");
        System.out.println("做阅读理解");
    }

}
