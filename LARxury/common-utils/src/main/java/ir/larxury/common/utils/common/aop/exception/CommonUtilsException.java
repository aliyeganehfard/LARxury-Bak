package ir.larxury.common.utils.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.Getter;

@Getter
public class CommonUtilsException extends GeneralException {

    public CommonUtilsException(ErrorCode message) {
        super(message);
    }

    public CommonUtilsException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode, cause);
    }
}
