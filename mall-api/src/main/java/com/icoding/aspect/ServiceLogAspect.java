package com.icoding.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// 声明为一个切面
@Aspect
@Component
public class ServiceLogAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);

  /**
   * 切面表达式：
   * execution 代表所要执行的表达式主体
   * 第一处 * 代表方法返回类型 *代表所有类型
   * 第二处 包名代表aop监控的类所在的包
   * 第三处 .. 代表该包以及其子包下的所有类方法
   * 第四处 * 代表类名，*代表所有类
   * 第五处 *(..) *代表类中的方法名，(..)表示方法中的任何参数
   */
  @Pointcut("execution(* com.icoding.controller.*.*(..))")
  private void pointCut() {

  }

  /**
   * AOP通知：
   * 1. 前置通知：在方法调用之前执行
   * 2. 后置通知：在方法正常调用之后执行
   * 3. 环绕通知：在方法调用之前和之后，都分别可以执行的通知
   * 4. 异常通知：如果在方法调用过程中发生异常，则通知
   * 5. 最终通知：在方法调用之后执行
   */
  @Around("pointCut()")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    LOGGER.info("====== 执行开始 {}.{} ======", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());

    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    if(executionTime > 3000) {
      LOGGER.error("====== 执行结束，耗时: {} 毫秒 ======", executionTime);
    } else if(executionTime > 2000) {
      LOGGER.warn("====== 执行结束，耗时: {} 毫秒 ======", executionTime);
    } else {
      LOGGER.info("====== 执行结束，耗时: {} 毫秒 ======", executionTime);
    }
    return proceed;
  }
}
