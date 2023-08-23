package ir.larxury.auth.service.controller;

import ir.larxury.auth.service.common.dto.authentication.OtpRes;
import ir.larxury.auth.service.common.dto.authentication.SignUpDto;
import ir.larxury.auth.service.common.aop.exception.AuthException;
import ir.larxury.auth.service.database.model.User;
import ir.larxury.auth.service.service.AuthService;
import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.common.utils.common.dto.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    private final ModelMapper mapper = new ModelMapper();

    @PostMapping("signUp")
    public ResponseEntity<GeneralResponse> signUp(@RequestBody @Valid SignUpDto req) {
        var user = mapper.map(req, User.class);
        var jwt = authService.signUp(user, req.getConfirmPassword());
        var res = GeneralResponse.successfulResponse(jwt, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("signIn")
    public ResponseEntity<GeneralResponse> signIn(@RequestBody MultiValueMap<String, String> formData) {
        String username = formData.getFirst("username");
        String password = formData.getFirst("password");
        var jwt = authService.signIn(username, password);
        var res = GeneralResponse.successfulResponse(jwt, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("otp/send")
    public ResponseEntity<GeneralResponse> sendOtp(@RequestParam("phoneNumber") String phoneNumber) {
        authService.sendOtp(phoneNumber);
        var res = GeneralResponse.successfulResponse(ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("otp/verify")
    public ResponseEntity<GeneralResponse> verifyOtp(
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("otpCode") String otpCode) {
        var jwt = authService.signInWithOtp(phoneNumber, otpCode);
        var res = GeneralResponse.successfulResponse(jwt, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("token/refresh")
    public ResponseEntity<GeneralResponse> refreshToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException(ErrorCode.REFRESH_TOKEN_IS_MISSING);
        }
        var token = authHeader.substring("Bearer ".length());
        var jwt = authService.refreshToken(token);
        var res = GeneralResponse.successfulResponse(jwt, ErrorCode.SUCCESSFUL);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}