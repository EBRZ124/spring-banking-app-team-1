package com.demo.banking.app.team1.service;

import com.demo.banking.app.team1.dto.TransferRequestDto;
import com.demo.banking.app.team1.dto.TransferResponseDto;

public interface TransferService {

    TransferResponseDto transfer(TransferRequestDto request);

}
