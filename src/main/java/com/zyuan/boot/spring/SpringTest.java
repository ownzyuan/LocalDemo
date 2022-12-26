package com.zyuan.boot.spring;

import com.zyuan.boot.redis.IRedisService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SpringTest.class);
        applicationContext.refresh();
        applicationContext.close();
    }

}
