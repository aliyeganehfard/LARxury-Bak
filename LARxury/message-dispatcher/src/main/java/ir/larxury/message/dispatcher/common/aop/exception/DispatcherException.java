package ir.larxury.message.dispatcher.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;
import lombok.Getter;

@Getter
public class DispatcherException extends RuntimeException {

    private ErrorCode errorCode;

    public DispatcherException(ErrorCode message) {
        super(message.getTechnicalMessage());
    }

    public DispatcherException(ErrorCode errorCode, Throwable cause ) {
        super(errorCode.getTechnicalMessage(), cause);
        this.errorCode = errorCode;
    }
}


