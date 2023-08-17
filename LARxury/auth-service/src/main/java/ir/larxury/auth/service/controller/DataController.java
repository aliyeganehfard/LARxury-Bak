package ir.larxury.auth.service.controller;

import ir.larxury.common.utils.service.UserSecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class DataController {

    @Autowired
    private UserSecurityService userSecurityService;
    private final ModelMapper mapper = new ModelMapper();

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("admin")
    public String admin(){
        return userSecurityService.getCurrentUser();
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("manager")
    public String manager(){
        return userSecurityService.getCurrentUser();
    }

    @PreAuthorize("hasAnyRole('SHOP_ADMIN')")
    @GetMapping("shopAdmin")
    public String shopAdmin(){
        return userSecurityService.getCurrentUser();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("user")
    public String user(){
        return userSecurityService.getCurrentUser();
    }

    @GetMapping("all")
    public String all(){
        return "hello all users";
    }

    @GetMapping("hello")
    public String hello(){
        return userSecurityService.getCurrentUser();
    }
}
