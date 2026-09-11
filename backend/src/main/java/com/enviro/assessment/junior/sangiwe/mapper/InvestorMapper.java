package com.enviro.assessment.junior.sangiwe.mapper;

import com.enviro.assessment.junior.sangiwe.dto.InvestorPortfolioDto;
import com.enviro.assessment.junior.sangiwe.entity.Investor;

import java.util.List;
import java.util.stream.Collectors;

public class InvestorMapper {

    private InvestorMapper() {
    }

    public static InvestorPortfolioDto toPortfolioDto(Investor investor) {
        List<com.enviro.assessment.junior.sangiwe.dto.ProductDto> productDtos =
                investor.getProducts().stream()
                        .map(ProductMapper::toDto)
                        .collect(Collectors.toList());

        return new InvestorPortfolioDto(
                investor.getId(),
                investor.getName(),
                investor.getAge(),
                productDtos
        );
    }
}