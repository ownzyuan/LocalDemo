package com.zyuan.boot.设计模式.策略模式.impl;

import com.zyuan.boot.设计模式.策略模式.ILearningService;
import org.springframework.stereotype.Service;

// 英语学习
@Service("English")
public class EnglishService implements ILearningService {

    @Override
    public void startLearning() {
        System.out.println("背单词");
        System.out.println("做听力练习");
        System.out.println("写个英语作文");
        System.out.println("看英文读物");
    }

}
