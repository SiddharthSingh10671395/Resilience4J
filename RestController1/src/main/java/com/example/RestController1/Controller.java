package com.example.RestController1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Controller {


    @RequestMapping("/greet")
    public String greet(){

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    @RequestMapping("/greet2")
    public String greet2(){
        return "hello22";
    }

}
