package com.enviro.assessment.junior.sangiwe.config;

import com.enviro.assessment.junior.sangiwe.entity.*;
import com.enviro.assessment.junior.sangiwe.repository.InvestorRepository;
import com.enviro.assessment.junior.sangiwe.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component   // Spring-managed bean so it gets picked up automatically
public class DataSeeder implements CommandLineRunner {

    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;

    // Constructor injection
    public DataSeeder(InvestorRepository investorRepository, ProductRepository productRepository) {
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        Investor thandiwe = new Investor();
        thandiwe.setName("Thandiwe Mokoena");
        thandiwe.setAge(70);   // over 65 — retirement withdrawal - allowed

        Investor sipho = new Investor();
        sipho.setName("Sipho Nkosi");
        sipho.setAge(40);      // under 65 — retirement withdrawal - blocked

        investorRepository.save(thandiwe);
        investorRepository.save(sipho);

        Product thandiweRetirement = new Product();
        thandiweRetirement.setName("Retirement Annuity");
        thandiweRetirement.setType(ProductType.RETIREMENT);
        thandiweRetirement.setBalance(new BigDecimal("500000.00"));
        thandiweRetirement.setInvestor(thandiwe);

        Product siphoGeneral = new Product();
        siphoGeneral.setName("Unit Trust");
        siphoGeneral.setType(ProductType.GENERAL);
        siphoGeneral.setBalance(new BigDecimal("100000.00"));
        siphoGeneral.setInvestor(sipho);

        Product siphoRetirement = new Product();
        siphoRetirement.setName("Retirement Annuity");
        siphoRetirement.setType(ProductType.RETIREMENT);
        siphoRetirement.setBalance(new BigDecimal("250000.00"));
        siphoRetirement.setInvestor(sipho);

        productRepository.saveAll(List.of(thandiweRetirement, siphoGeneral, siphoRetirement));

        System.out.println("Seed data loaded: 2 investors, 3 products");
    }
}