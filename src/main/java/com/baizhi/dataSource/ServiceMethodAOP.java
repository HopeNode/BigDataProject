package com.baizhi.dataSource;

import com.baizhi.annotation.SlaveDB;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(0)//值越小 优先级越高
public class ServiceMethodAOP {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMethodAOP.class);

    @Around("execution(* com.baizhi.service..*.*(..))")
    public Object round(ProceedingJoinPoint proceedingJoinPoint) {
        Object proceed = null;
        try {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();
            //通过方法上注解来进行判断是读方法和写方法
            boolean present = method.isAnnotationPresent(SlaveDB.class);
            //如果为false则为写方法
            OperType operType = null;
            if (!present) {
                operType = OperType.WRITE;
            } else {
                operType = OperType.READ;
            }
            OperTypeContextHolder.setOperType(operType);
            LOGGER.debug("当前操作类型为：" + operType);
            proceed = proceedingJoinPoint.proceed();
            //清除线程变量
            OperTypeContextHolder.clearOperType();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
