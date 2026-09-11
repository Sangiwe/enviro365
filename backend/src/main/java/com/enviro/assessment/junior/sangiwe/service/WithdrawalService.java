package com.enviro.assessment.junior.sangiwe.service;

import com.enviro.assessment.junior.sangiwe.entity.*;
import com.enviro.assessment.junior.sangiwe.exception.*;
import com.enviro.assessment.junior.sangiwe.repository.ProductRepository;
import com.enviro.assessment.junior.sangiwe.repository.WithdrawalNoticeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service   // marks this as a Spring-managed business logic bean, injectable elsewhere
public class WithdrawalService {

    private final ProductRepository productRepository;
    private final WithdrawalNoticeRepository withdrawalNoticeRepository;

    public WithdrawalService(ProductRepository productRepository,
                             WithdrawalNoticeRepository withdrawalNoticeRepository) {
        this.productRepository = productRepository;
        this.withdrawalNoticeRepository = withdrawalNoticeRepository;
    }

    public WithdrawalNotice processWithdrawal(Long productId, BigDecimal amount) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        // Rule 1: retirement products can only be withdrawn from if the investor is over 65.
        // Checking product.getInvestor() here rather than passing age in separately,
        // since age belongs to the investor and shouldn't be trusted from the request body.
        if (product.getType() == ProductType.RETIREMENT && product.getInvestor().getAge() <= 65) {
            throw new AgeRestrictionException(
                    "Retirement withdrawals are only allowed for investors over 65.");
        }

        // Rule 2: can't withdraw more than what's actually in the product.
        if (amount.compareTo(product.getBalance()) > 0) {
            throw new InsufficientBalanceException(
                    "Withdrawal amount exceeds available balance.");
        }

        // Rule 3: even if the balance covers it, can't take more than 90% in one go.
        // Using BigDecimal multiplication rather than converting to double, to avoid
        // floating point rounding issues with money.
        BigDecimal maxAllowed = product.getBalance().multiply(new BigDecimal("0.90"));
        if (amount.compareTo(maxAllowed) > 0) {
            throw new WithdrawalLimitExceededException(
                    "Withdrawal amount exceeds 90% of the available balance.");
        }

        // All rules passed — deduct and record the withdrawal.
        product.setBalance(product.getBalance().subtract(amount));
        productRepository.save(product);

        WithdrawalNotice notice = new WithdrawalNotice();
        notice.setProduct(product);
        notice.setAmount(amount);
        notice.setDateRequested(LocalDate.now());
        notice.setStatus(WithdrawalStatus.APPROVED);

        return withdrawalNoticeRepository.save(notice);
    }
}