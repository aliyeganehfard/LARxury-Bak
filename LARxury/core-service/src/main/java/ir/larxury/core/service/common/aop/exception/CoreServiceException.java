package ir.larxury.core.service.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;
import lombok.Data;
import lombok.Getter;

@Getter
public class CoreServiceException extends RuntimeException {

    private ErrorCode errorCode;

    public CoreServiceException(ErrorCode message) {
        super(message.getTechnicalMessage());
    }

    public CoreServiceException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode.getTechnicalMessage(), cause);
        this.errorCode = errorCode;
    }
}