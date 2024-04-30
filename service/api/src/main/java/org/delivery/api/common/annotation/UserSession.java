package org.delivery.api.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)//파라미터에 사용
@Retention(RetentionPolicy.RUNTIME)//런타임 중 사용

//user의 정보를 표시하는 어노테이션
public @interface UserSession {
}
