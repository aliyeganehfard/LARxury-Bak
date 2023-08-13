package ir.larxury.common.utils.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GeneralResponse implements Serializable {
    private Integer rsCode;
    private String message;
    private Boolean isSuccess;
    private Object resultData;


    public static GeneralResponse successfulResponse(Object resultData, ErrorCode errorCode){
        return GeneralResponse.builder()
                .rsCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .isSuccess(true)
                .resultData(resultData)
                .build();
    }

    public static GeneralResponse unsuccessfulResponse(ErrorCode errorCode){
        return GeneralResponse.builder()
                .rsCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .isSuccess(false)
                .build();
    }
    public static GeneralResponse unsuccessfulResponse(ErrorCode errorCode, String customMessage){
        return GeneralResponse.builder()
                .rsCode(errorCode.getCode())
                .message(customMessage)
                .isSuccess(false)
                .build();
    }
}
