package org.delivery.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.common.error.ErrorCode;
import org.delivery.common.error.ErrorCodeIfs;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    public static Result ok(){
        return Result.builder()
                .resultCode(ErrorCode.OK.getErrorCode())
                .resultMessage(ErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }
    public static Result ERROR(ErrorCodeIfs errorCodeIfs){//추상화
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription("에러 발샐")
                .build();
    }
    public static Result ERROR(ErrorCodeIfs errorCodeIfs,Throwable tx){//비추임
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(tx.getLocalizedMessage())//에러 발생 시, 실제 메시지 출력(stack trace)
                .build();
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs,String description){
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage(errorCodeIfs.getDescription())
                .resultDescription(description)//특정 에러 메시지
                .build();
    }
}
