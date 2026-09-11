package com.enviro.assessment.junior.sangiwe.dto;

import com.enviro.assessment.junior.sangiwe.entity.WithdrawalStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalResponseDto {
    private Long id;
    private Long productId;
    private BigDecimal amount;
    private LocalDate dateRequested;
    private WithdrawalStatus status;
}