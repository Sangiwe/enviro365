package com.enviro.assessment.junior.sangiwe.repository;

import com.enviro.assessment.junior.sangiwe.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
}