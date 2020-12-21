package com.example.RestController2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Controller {


    @RequestMapping("/greet")
    public String greet(){
        return "hello";
    }

    @RequestMapping("/greet2")
    public String greet2(){
        return "hello22";
    }

}
