package ir.larxury.core.service.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class Controller {

    @GetMapping("hello")
    public String hello(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
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
