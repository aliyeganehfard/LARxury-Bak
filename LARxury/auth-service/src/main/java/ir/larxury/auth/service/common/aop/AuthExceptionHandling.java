package ir.larxury.auth.service.common.aop;

import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandling {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GeneralResponse> handleAuthException(AuthException authException){
        authException.printStackTrace();
        var res = GeneralResponse.unsuccessfulResponse(authException.getErrorCode());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
