package com.enviro.assessment.junior.sangiwe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvestorPortfolioDto {
    private Long id;
    private String name;
    private int age;
    private List<ProductDto> products;
}