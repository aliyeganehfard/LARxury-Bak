package ir.larxury.core.service.common.aop;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.CommonUtilsException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CoreExceptionHandling {

    @ExceptionHandler(CoreServiceException.class)
    public ResponseEntity<GeneralResponse> handleCore(CoreServiceException coreServiceException){
        coreServiceException.printStackTrace();
        var res = GeneralResponse.unsuccessfulResponse(coreServiceException.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

}
