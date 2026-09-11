package com.enviro.assessment.junior.sangiwe.controller;

import com.enviro.assessment.junior.sangiwe.dto.WithdrawalRequestDto;
import com.enviro.assessment.junior.sangiwe.dto.WithdrawalResponseDto;
import com.enviro.assessment.junior.sangiwe.entity.WithdrawalNotice;
import com.enviro.assessment.junior.sangiwe.mapper.WithdrawalMapper;
import com.enviro.assessment.junior.sangiwe.service.WithdrawalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                        // combines @Controller + @ResponseBody: every method's
                                       // return value is written straight to the HTTP response body as JSON
@RequestMapping("/api/withdrawals")    // base path for every endpoint in this class
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @PostMapping
    public ResponseEntity<WithdrawalResponseDto> createWithdrawal(
            @Valid @RequestBody WithdrawalRequestDto request) {

        WithdrawalNotice notice = withdrawalService.processWithdrawal(
                request.getProductId(), request.getAmount());

        WithdrawalResponseDto response = WithdrawalMapper.toDto(notice);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}