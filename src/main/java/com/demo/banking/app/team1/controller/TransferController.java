package com.demo.banking.app.team1.controller;

import com.demo.banking.app.team1.dto.TransferRequestDto;
import com.demo.banking.app.team1.dto.TransferResponseDto;
import com.demo.banking.app.team1.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> createTransfer(@Valid @RequestBody TransferRequestDto request) {
        log.info("Received transfer request: {}", request);

        TransferResponseDto response = transferService.transfer(request);

        log.info("Transfer completed successfully: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
