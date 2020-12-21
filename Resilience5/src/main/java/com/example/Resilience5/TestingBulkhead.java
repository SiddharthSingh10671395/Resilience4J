package com.example.Resilience5;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestingBulkhead {

    @RequestMapping("getc")
    public String serviceC(){
        RestTemplate restTemplate=new RestTemplate();
        return  restTemplate.getForObject("http://localhost:8082/test/greet",String.class);

    }

    @RequestMapping("getb")
    public String serviceb(){
        RestTemplate restTemplate=new RestTemplate();
        return  restTemplate.getForObject("http://localhost:8081/test/greet",String.class);

    }

    @RequestMapping("fillthread")
    public String fillThread(){
                BulkHead1.fillThread();
                return "done";
    }
}
