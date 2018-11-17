package com.common.baselib.mvp;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  // 注入属性
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectPresenter {
}
