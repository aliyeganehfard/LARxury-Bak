package ir.larxury.auth.server.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class DataController {

    private final ModelMapper mapper = new ModelMapper();

    @GetMapping("admin")
    public String admin(){
        return "hello admin";
    }

    @GetMapping("all")
    public String all(){
        return "hello all users";
    }

    @GetMapping("hello")
    public String hello(){
        return "say hello to me!";
    }
}
