package com.enviro.assessment.junior.sangiwe.config;

import com.enviro.assessment.junior.sangiwe.entity.*;
import com.enviro.assessment.junior.sangiwe.repository.InvestorRepository;
import com.enviro.assessment.junior.sangiwe.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

// Seeds some starter data on startup so I have real investors/products to test
// the withdrawal rules against, instead of hitting empty endpoints.
// Using CommandLineRunner + the actual repositories rather than data.sql so this
// goes through the same save() path the real app will use.
@Component
public class DataSeeder implements CommandLineRunner {

    private final InvestorRepository investorRepository;
    private final ProductRepository productRepository;

    public DataSeeder(InvestorRepository investorRepository, ProductRepository productRepository) {
        this.investorRepository = investorRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        // Deliberately one investor over 65 and one under, so I can prove the
        // retirement age rule actually blocks/allows correctly, not just guess.
        Investor thandiwe = new Investor();
        thandiwe.setName("Thandiwe Mokoena");
        thandiwe.setAge(70);

        Investor sipho = new Investor();
        sipho.setName("Sipho Nkosi");
        sipho.setAge(40);

        investorRepository.save(thandiwe);
        investorRepository.save(sipho);

        // Product type (RETIREMENT/GENERAL) lives here, not on the withdrawal request.
        // Decided this because "retirement account" reads as a property of the product
        // itself in the brief, not something a user selects when withdrawing.
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

        // Sipho also has a retirement product even though he's under 65 —
        // this is the case that should get REJECTED once I add the age rule.
        Product siphoRetirement = new Product();
        siphoRetirement.setName("Retirement Annuity");
        siphoRetirement.setType(ProductType.RETIREMENT);
        siphoRetirement.setBalance(new BigDecimal("250000.00"));
        siphoRetirement.setInvestor(sipho);

        productRepository.saveAll(List.of(thandiweRetirement, siphoGeneral, siphoRetirement));

        System.out.println("Seed data loaded: 2 investors, 3 products");
    }
}