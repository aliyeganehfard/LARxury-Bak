package ir.larxury.auth.server.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;

public class AuthException extends GeneralException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
