package com.bfhl.controller;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BfhlController {

    private final BfhlService service;

    public BfhlController(BfhlService service) {
        this.service = service;
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> process(@Valid @RequestBody BfhlRequest request) {
        BfhlResponse response = service.process(request);
        return ResponseEntity.ok(response); // 200 OK for success
    }
}