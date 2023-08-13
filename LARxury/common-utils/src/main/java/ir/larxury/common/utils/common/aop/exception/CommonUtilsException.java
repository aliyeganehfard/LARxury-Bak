package ir.larxury.common.utils.common.aop.exception;

import ir.larxury.common.utils.common.aop.ErrorCode;

public class CommonUtilsException extends GeneralException{

    public CommonUtilsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
