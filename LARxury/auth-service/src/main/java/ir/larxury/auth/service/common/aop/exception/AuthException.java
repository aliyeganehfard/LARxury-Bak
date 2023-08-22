package ir.larxury.auth.service.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private ErrorCode errorCode;

    public AuthException(ErrorCode message) {
        super(message.getTechnicalMessage());
    }

    public AuthException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode.getTechnicalMessage(), cause);
        this.errorCode = errorCode;
    }
}


