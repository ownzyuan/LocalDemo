package com.zyuan.boot.aoptest.aop;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component("com.zy")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopConfig {
}
