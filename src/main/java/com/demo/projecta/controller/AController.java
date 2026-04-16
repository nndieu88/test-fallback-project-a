package com.demo.projecta.controller;

import com.demo.projecta.service.BClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {

    private final BClientService bClientService;

    public AController(BClientService bClientService) {
        this.bClientService = bClientService;
    }

    @GetMapping("/api/a/proxy")
    public ResponseEntity<?> proxy(@RequestParam(defaultValue = "ok") String mode) {
        return bClientService.callB(mode);
    }

    @GetMapping("/api/a/proxy-v2")
    public ResponseEntity<?> proxyV2(@RequestParam(defaultValue = "ok") String mode) {
        return bClientService.callBV2(mode);
    }
}
