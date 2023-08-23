package ir.larxury.message.dispatcher.aop;

import ir.larxury.common.utils.common.dto.GeneralResponse;
import ir.larxury.message.dispatcher.aop.exception.DispatcherException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DispatcherExceptionHandling {

    @ExceptionHandler(DispatcherException.class)
    public ResponseEntity<GeneralResponse> handleAuthException(DispatcherException dispatcherException){
        dispatcherException.printStackTrace();
        var res = GeneralResponse.unsuccessfulResponse(dispatcherException.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
