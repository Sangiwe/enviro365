package com.enviro.assessment.junior.sangiwe.dto;

import com.enviro.assessment.junior.sangiwe.entity.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private ProductType type;
    private BigDecimal balance;
}