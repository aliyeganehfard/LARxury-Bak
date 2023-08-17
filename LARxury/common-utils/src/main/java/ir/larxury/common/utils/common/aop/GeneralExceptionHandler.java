package ir.larxury.common.utils.common.aop;

import ir.larxury.common.utils.common.aop.exception.CommonUtilsException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(CommonUtilsException.class)
    public ResponseEntity<GeneralResponse> handleCommonUtilsException(CommonUtilsException commonUtilsException){
        var res = GeneralResponse.unsuccessfulResponse(commonUtilsException.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleRequestValidationException(MethodArgumentNotValidException e){
        var bindingResult = e.getBindingResult();
        var errorMessage = bindingResult.getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getMessage());
        var res = GeneralResponse.unsuccessfulResponse(ErrorCode.METHOD_ARGUMENT_NOT_VALID,errorMessage);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<GeneralResponse> handleException(Exception exception){
//        var res = GeneralResponse.unsuccessfulResponse(ErrorCode.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
//    }
}
