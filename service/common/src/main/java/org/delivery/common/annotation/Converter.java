package org.delivery.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
//dto <-> entity, A.class <-> B.class 등의 데이터 변환에 대한 처리
public @interface Converter {

    @AliasFor(annotation = Service.class)
    String value() default "";
}
