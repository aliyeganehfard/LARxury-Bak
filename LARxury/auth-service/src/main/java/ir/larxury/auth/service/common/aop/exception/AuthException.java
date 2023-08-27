package ir.larxury.auth.service.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;
import lombok.Getter;

@Getter
public class AuthException extends GeneralException {

    public AuthException(ErrorCode message) {
        super(message);
    }

    public AuthException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode, cause);
    }
}


