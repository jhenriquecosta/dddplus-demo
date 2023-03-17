package org.example.bp.oms.isv.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

// 业务开展包里可以自己实现AOP机制
@Aspect
@Slf4j
@Component
public class AutoLoggerAspect implements InitializingBean {

    public AutoLoggerAspect() {
        log.info("Spring created instance AutoLoggerAspect!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("AutoLoggerAspect 注册 Spring lifecycle ok");
    }

    @Around("@annotation(autoLogger)")
    public Object around(ProceedingJoinPoint pjp, AutoLogger autoLogger) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        log.info("{}.{} 入参:{}", signature.getDeclaringTypeName(), signature.getName(), args);
        try {
            return pjp.proceed();
        } catch (Throwable cause) {
            log.error("{}.{} error", signature.getDeclaringTypeName(), signature.getName(), cause);
            throw cause;
        }
    }
}
