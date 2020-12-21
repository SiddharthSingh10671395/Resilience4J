package com.example.Resilience5;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class Retry1 {
    static void test1(){
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(4)
                .waitDuration(Duration.ofMillis(1000))
                .build();

// Create a RetryRegistry with a custom global configuration
        RetryRegistry registry = RetryRegistry.of(config);

// Get or create a Retry from the registry -
// Retry will be backed by the default config
        Retry retryWithDefaultConfig = registry.retry("name1");

        CheckedFunction0<String> retryableSupplier = Retry
                .decorateCheckedSupplier(retryWithDefaultConfig, CircuitBreaker5::print);

        Try<String> result = Try.of(retryableSupplier)
                .recover((throwable) -> "Hello world from recovery function");
        System.out.println(result);
    }

    public static void main(String[] args) {
        Retry1.test1();
    }
}
