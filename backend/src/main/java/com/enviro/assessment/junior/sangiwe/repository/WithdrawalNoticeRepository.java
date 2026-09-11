package com.enviro.assessment.junior.sangiwe.repository;

import com.enviro.assessment.junior.sangiwe.entity.WithdrawalNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface WithdrawalNoticeRepository extends JpaRepository<WithdrawalNotice, Long> {

    // Filters are optional — if a parameter is null, that condition is skipped
    // entirely (the "OR :param IS NULL" trick), so this one query handles
    // "no filters", "investor only", "date range only", or "both" all at once.
    @Query("SELECT w FROM WithdrawalNotice w " +
            "WHERE (:investorId IS NULL OR w.product.investor.id = :investorId) " +
            "AND (:startDate IS NULL OR w.dateRequested >= :startDate) " +
            "AND (:endDate IS NULL OR w.dateRequested <= :endDate)")
    List<WithdrawalNotice> findWithFilters(
            @Param("investorId") Long investorId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}