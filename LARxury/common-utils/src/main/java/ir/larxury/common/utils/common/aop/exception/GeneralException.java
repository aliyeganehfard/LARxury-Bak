package ir.larxury.common.utils.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    public GeneralException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getTechnicalMessage(), cause);
        this.errorCode = errorCode;
    }
}
