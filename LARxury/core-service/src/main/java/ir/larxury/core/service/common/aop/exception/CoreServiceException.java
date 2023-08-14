package ir.larxury.core.service.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.GeneralException;
import lombok.Data;

public class CoreServiceException extends GeneralException {
    public CoreServiceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
