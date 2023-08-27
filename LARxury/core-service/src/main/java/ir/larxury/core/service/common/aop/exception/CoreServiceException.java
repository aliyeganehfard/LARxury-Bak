package ir.larxury.core.service.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;
import lombok.Data;
import lombok.Getter;

@Getter
public class CoreServiceException extends GeneralException {

    public CoreServiceException(ErrorCode message) {
        super(message);
    }

    public CoreServiceException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode, cause);
    }
}

