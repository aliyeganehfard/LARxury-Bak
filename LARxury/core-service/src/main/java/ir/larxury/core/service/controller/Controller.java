package ir.larxury.core.service.controller;

import ir.larxury.core.service.provider.MessageDispatcherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/")
public class Controller {

    @Autowired
    private MessageDispatcherProvider messageDispatcherProvider;

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
        messageDispatcherProvider.instantDelivery("hello","ali","aliyeganefard@gmail.com");
        return "allUsers";
    }

}
