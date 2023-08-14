package ir.larxury.core.service.controller;

import ir.larxury.common.utils.common.aop.ErrorCode;
import ir.larxury.core.service.common.aop.exception.CoreServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class Controller {

    @GetMapping("hello")
    public String hello(){
        throw new CoreServiceException(ErrorCode.RSA_TROUBLE_READ_PRIVATE_KEY);
    }

    @GetMapping("admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("user")
    public String user(){
        return "user";
    }

    @GetMapping("allUsers")
    public String allUsers(){
        return "allUsers";
    }

}
