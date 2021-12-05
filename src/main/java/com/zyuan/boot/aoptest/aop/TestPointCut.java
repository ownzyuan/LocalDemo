package com.zyuan.boot.aoptest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestPointCut {

    /**
     * execution中参数的解释：* com.zyuan.boot.aoptest.service..*.*(..)
     * "*"：modifiers-pattern：方法的可见性，public，protected
     * " "：中间这个空格其实是ret-type-pattern：方法的返回值类型，可以直接空格不写
     */
    // service的切点
    @Pointcut("execution(* com.zyuan.boot.aoptest.service..*.*(..))")
//    @Pointcut("args(java.lang.String)")
    public void pointCutService() {

    }

    // mapper的切点
    @Pointcut("execution(* com.zyuan.boot.aoptest.mapper..*.*(..))")
    public void pointCutMapper() {

    }

    // 指定在pointCutService这个pointCut之前
//    @Before("pointCutService()")
//    public void adviceService() {
//        System.out.println("aop before------xxx");
//    }

    // 指定在pointCutMapper这个pointCut之后
    @After("pointCutMapper()")
    public void adviceMapper() {
        System.out.println("aop after-------yyy");
    }

    //    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    @Pointcut("@annotation(com.zyuan.boot.aoptest.aop.TestAopConfig)")
    public void pointCutAnnotation() {

    }

    @Before("pointCutAnnotation()")
    public void adviceAnnotation(){
        System.out.println("annotation before-----111");
    }

    @Pointcut("args(java.lang.String,java.lang.String)")
    public void pointCutArgs() {

    }

    @Pointcut("execution(* com.zyuan.boot.aoptest.service..*.*(..))&&pointCutArgs()")
    public void pointCutMixture() {

    }

    @Before("pointCutMixture()")
    public void adviceMixtureBefore() {
        System.out.println("mixture----yyy");
    }

    @After("pointCutMixture()")
    public void adviceMixtureAfter() {
        System.out.println("mixture-----xxx");
    }

    @Pointcut("this(com.zyuan.boot.aoptest.service.ITestAop)")
    public void pointCutThis() {

    }

//    @Before("pointCutThis()")
//    public void adviceThis() {
//        System.out.println("this-------xxx");
//    }

    @Pointcut("args(java.lang.String,java.lang.String,java.lang.String)")
    public void pointCutArgs02() {

    }

    @Pointcut("execution(* com.zyuan.boot.aoptest.service..*.*(..))&&pointCutArgs02()")
    public void pointCutAround() {

    }

    @Around("pointCutAround()")
    public void adviceAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around-----before");
        Object[] args = pjp.getArgs();
        Object[] otherArgs = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String str = (String) arg;
            str += 1;
            otherArgs[i] = str;
            System.out.println(arg);
        }
        // proceed方法：调用后spring才会执行用户的方法
        pjp.proceed(args);
        System.out.println("around-----after");
    }

}
