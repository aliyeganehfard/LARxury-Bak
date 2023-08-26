package ir.larxury.common.utils.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.Getter;
@Getter
public class CommonUtilsException extends RuntimeException {

    private ErrorCode errorCode;

    public CommonUtilsException(ErrorCode message) {
        super(message.getTechnicalMessage());
    }

    public CommonUtilsException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode.getTechnicalMessage(), cause);
        this.errorCode = errorCode;
    }
}