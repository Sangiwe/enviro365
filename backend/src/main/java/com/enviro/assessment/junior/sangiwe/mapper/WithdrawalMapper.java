package com.enviro.assessment.junior.sangiwe.mapper;

import com.enviro.assessment.junior.sangiwe.dto.WithdrawalResponseDto;
import com.enviro.assessment.junior.sangiwe.entity.WithdrawalNotice;

public class WithdrawalMapper {

    private WithdrawalMapper() {
    }

    public static WithdrawalResponseDto toDto(WithdrawalNotice notice) {
        return new WithdrawalResponseDto(
                notice.getId(),
                notice.getProduct().getId(),
                notice.getAmount(),
                notice.getDateRequested(),
                notice.getStatus()
        );
    }
}