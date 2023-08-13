package ir.larxury.auth.server.common.aop;

import ir.larxury.auth.server.common.aop.exception.AuthException;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.aop.exception.CommonUtilsException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GeneralResponse> handleAuthException(AuthException authException){
        var res = GeneralResponse.unsuccessfulResponse(authException.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }

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
}
