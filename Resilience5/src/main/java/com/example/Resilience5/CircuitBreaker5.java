package com.example.Resilience5;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.SupplierUtils;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
//working code
public class CircuitBreaker5 {


    public static  String print(){
        RestTemplate restTemplate=new RestTemplate();
//        try {
//            Thread.sleep(4000);
//            throw new Exception();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return restTemplate.getForObject("http://localhost:8081/test/greet", String.class);
    }
    public static  String printNew(int times){
        RestTemplate restTemplate=new RestTemplate();
//        try {
//            Thread.sleep(4000);
//            throw new Exception();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return restTemplate.getForObject("http://localhost:8081/test/greet", String.class);
    }
    public static  String print2(){
        RestTemplate restTemplate=new RestTemplate();
//        try {
//            Thread.sleep(4000);
//            throw new Exception();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return restTemplate.getForObject("http://localhost:8082/movies/hello", String.class);
    }


    public static CircuitBreaker lend(){
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))

                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry
                .circuitBreaker("name");

        return circuitBreaker;
    }
        //this function return "hello " from recovery when our link stops working
    public static void test(){

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(CircuitBreaker5.lend(), CircuitBreaker5::print);

        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> "Hello from Recovery").get();
//        String result = circuitBreaker
//                .executeSupplier(CircuitBreaker5::print);


        System.out.println(result);

    }

    public static void test2(){
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(1000))

                .permittedNumberOfCallsInHalfOpenState(2)
                .slidingWindowSize(2)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();


        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);


//        CircuitBreaker circuitBreaker = circuitBreakerRegistry
//                .circuitBreaker("name");
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");


        CheckedFunction0<String> checkedSupplier =
                CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
                    throw new RuntimeException("BAM!");
                });
        Try<String> result = Try.of(checkedSupplier)
                .recover(throwable -> "Hello Recovery");


        System.out.println(result);





    }
   //If you want to recover from an exception before the CircuitBreaker records it as a failure, you can do the following:
     public static void  test3(){
         Supplier<String> supplier = () -> {
             throw new RuntimeException("BAM!");
         };
         Supplier<String> supplier2 = CircuitBreaker5::print;

         Supplier<String> supplierWithRecovery = SupplierUtils
                 //test it with supplier also
                 .recover(supplier2, (exception) -> "Hello Recovery");
         String result = CircuitBreaker5.lend().executeSupplier(supplierWithRecovery);

         System.out.println(result);

     }


    public static void main(String[] args) {
            //CircuitBreaker5.test();
            //CircuitBreaker5.test2();
        CircuitBreaker5.test3();

    }
}
