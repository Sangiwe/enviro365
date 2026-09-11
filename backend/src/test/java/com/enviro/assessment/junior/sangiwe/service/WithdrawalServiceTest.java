package com.enviro.assessment.junior.sangiwe.service;

import com.enviro.assessment.junior.sangiwe.entity.*;
import com.enviro.assessment.junior.sangiwe.exception.*;
import com.enviro.assessment.junior.sangiwe.repository.ProductRepository;
import com.enviro.assessment.junior.sangiwe.repository.WithdrawalNoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WithdrawalServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WithdrawalNoticeRepository withdrawalNoticeRepository;

    @InjectMocks
    private WithdrawalService withdrawalService;

    private Investor youngInvestor;
    private Investor retiredInvestor;
    private Product retirementProduct;
    private Product generalProduct;

    @BeforeEach
    void setUp() {
        youngInvestor = new Investor();
        youngInvestor.setAge(40);

        retiredInvestor = new Investor();
        retiredInvestor.setAge(70);

        retirementProduct = new Product();
        retirementProduct.setId(1L);
        retirementProduct.setType(ProductType.RETIREMENT);
        retirementProduct.setBalance(new BigDecimal("100000.00"));
        retirementProduct.setInvestor(youngInvestor);

        generalProduct = new Product();
        generalProduct.setId(2L);
        generalProduct.setType(ProductType.GENERAL);
        generalProduct.setBalance(new BigDecimal("100000.00"));
        generalProduct.setInvestor(youngInvestor);

        lenient().when(withdrawalNoticeRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void rejectsRetirementWithdrawalUnderAge65() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(retirementProduct));

        assertThatThrownBy(() ->
                withdrawalService.processWithdrawal(1L, new BigDecimal("1000")))
                .isInstanceOf(AgeRestrictionException.class);
    }

    @Test
    void allowsRetirementWithdrawalOverAge65() {
        retirementProduct.setInvestor(retiredInvestor);
        when(productRepository.findById(1L)).thenReturn(Optional.of(retirementProduct));

        WithdrawalNotice notice = withdrawalService.processWithdrawal(1L, new BigDecimal("1000"));

        assertThat(notice.getStatus()).isEqualTo(WithdrawalStatus.APPROVED);
    }

    @Test
    void rejectsWithdrawalExceedingBalance() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(generalProduct));

        assertThatThrownBy(() ->
                withdrawalService.processWithdrawal(2L, new BigDecimal("150000")))
                .isInstanceOf(InsufficientBalanceException.class);
    }

    @Test
    void rejectsWithdrawalExceeding90PercentOfBalance() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(generalProduct));

        assertThatThrownBy(() ->
                withdrawalService.processWithdrawal(2L, new BigDecimal("95000")))
                .isInstanceOf(WithdrawalLimitExceededException.class);
    }

    @Test
    void allowsWithdrawalWithinLimitsAndDeductsBalance() {
        when(productRepository.findById(2L)).thenReturn(Optional.of(generalProduct));


        WithdrawalNotice notice = withdrawalService.processWithdrawal(2L, new BigDecimal("50000"));

        assertThat(notice.getStatus()).isEqualTo(WithdrawalStatus.APPROVED);
        assertThat(generalProduct.getBalance()).isEqualByComparingTo("50000.00");
        verify(productRepository).save(generalProduct);
    }
}