package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //注解的使用位置
@Retention(RetentionPolicy.RUNTIME)  //运行时生效
public @interface ClearCache {
    //清除缓冲的注解
}
