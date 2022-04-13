package com.zyuan.boot.测试线程池;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestNewThreadPool {

    public static void main(String[] args) {
        ThreadFactory myThreadFactory = new ThreadFactoryBuilder().setNameFormat("abc").build();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(1024), myThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

}
