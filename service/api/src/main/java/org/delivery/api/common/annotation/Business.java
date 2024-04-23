package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//실행 중에 @ 적용
@Service//이 @Business은 @Service가 있기에 스프링에서 자동으로 감지 가능
//+ @Business가 있는 클래스들은 bin으로 등록
public @interface Business {

    @AliasFor(annotation = Service.class)//@Service 안에 있는 클래스를 지정
    String value() default "";//@Service에 있음 ctrl + 클릭
}
