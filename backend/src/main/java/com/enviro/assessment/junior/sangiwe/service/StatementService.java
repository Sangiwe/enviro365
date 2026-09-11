package com.enviro.assessment.junior.sangiwe.service;

import com.enviro.assessment.junior.sangiwe.entity.WithdrawalNotice;
import com.enviro.assessment.junior.sangiwe.repository.WithdrawalNoticeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatementService {

    private final WithdrawalNoticeRepository withdrawalNoticeRepository;

    public StatementService(WithdrawalNoticeRepository withdrawalNoticeRepository) {
        this.withdrawalNoticeRepository = withdrawalNoticeRepository;
    }

    public String generateCsv(Long investorId, LocalDate startDate, LocalDate endDate) {

        List<WithdrawalNotice> notices =
                withdrawalNoticeRepository.findWithFilters(investorId, startDate, endDate);

        StringBuilder csv = new StringBuilder();
        csv.append("Withdrawal ID,Investor Name,Product Name,Amount,Date Requested,Status\n");

        for (WithdrawalNotice notice : notices) {
            csv.append(notice.getId()).append(",")
                    .append(notice.getProduct().getInvestor().getName()).append(",")
                    .append(notice.getProduct().getName()).append(",")
                    .append(notice.getAmount()).append(",")
                    .append(notice.getDateRequested()).append(",")
                    .append(notice.getStatus())
                    .append("\n");
        }

        return csv.toString();
    }
}