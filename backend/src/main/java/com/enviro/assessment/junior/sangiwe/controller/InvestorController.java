package com.enviro.assessment.junior.sangiwe.controller;

import com.enviro.assessment.junior.sangiwe.dto.InvestorPortfolioDto;
import com.enviro.assessment.junior.sangiwe.entity.Investor;
import com.enviro.assessment.junior.sangiwe.mapper.InvestorMapper;
import com.enviro.assessment.junior.sangiwe.repository.InvestorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investors")
public class InvestorController {

    private final InvestorRepository investorRepository;

    public InvestorController(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvestorPortfolioDto> getPortfolio(@PathVariable Long id) {

        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Investor not found: " + id));

        return ResponseEntity.ok(InvestorMapper.toPortfolioDto(investor));
    }
}