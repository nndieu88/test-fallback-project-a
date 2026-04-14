package com.demo.projecta.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class BClientService {

    private final RestTemplate bRestTemplate;

    public BClientService(RestTemplate bRestTemplate) {
        this.bRestTemplate = bRestTemplate;
    }

    //    @TimeLimiter(name = "bService")
    @CircuitBreaker(name = "bService", fallbackMethod = "fallback")
    @Retry(name = "bService", fallbackMethod = "fallback")
    public ResponseEntity<?> callB(String mode) {
        var res = bRestTemplate.getForObject("/api/b/data?mode={mode}", String.class, mode);
        return ResponseEntity.ok(Map.of("data", res));
    }

    public ResponseEntity<?> fallback(String mode, Throwable throwable) {
        String message = "Fallback from A: B is unavailable/slow. mode=" + mode
                + ", reason=" + throwable.getClass().getSimpleName();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("data", message));
    }
}
