package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    /* Api: object인 body와 result를 감싸는 클래스
{
 “result” : {                  result: object
 “result_code” : 200,
 “result_message” : “OK”,
 “result_description” : “성공”,
 },
 “body” : {                    body: object, 제네릭 타입으로 형식이나 내용 모름
 …
 …
 }
}
     */
    private Result result;
    @Valid
    private T body;

    //Api의 리스펀스 ok를 내리는 메서드
    public static <T> Api<T> ok(T data){
        var api=new Api<T>();
        api.result=Result.ok();//Result이 ok 메서드가 static 형이라 생성자가 있을 필요 없음
        api.body=data;
        return api;
    }
}
