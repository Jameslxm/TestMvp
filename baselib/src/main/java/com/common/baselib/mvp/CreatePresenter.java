package com.common.baselib.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)   // 可以给一个类型进行注解，比如类、接口、枚举
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<?>[] value() default {};
}