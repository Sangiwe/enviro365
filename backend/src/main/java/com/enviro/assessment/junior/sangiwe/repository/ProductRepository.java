package com.enviro.assessment.junior.sangiwe.repository;

import com.enviro.assessment.junior.sangiwe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}