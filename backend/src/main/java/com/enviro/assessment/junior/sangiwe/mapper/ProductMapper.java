package com.enviro.assessment.junior.sangiwe.mapper;

import com.enviro.assessment.junior.sangiwe.dto.ProductDto;
import com.enviro.assessment.junior.sangiwe.entity.Product;

public class ProductMapper {

    // Private constructor — this class only ever holds static methods,
    // so there's no reason anyone should be able to do "new ProductMapper()".
    private ProductMapper() {
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getType(),
                product.getBalance()
        );
    }
}