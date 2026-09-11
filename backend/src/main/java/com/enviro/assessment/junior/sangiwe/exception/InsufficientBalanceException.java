package com.enviro.assessment.junior.sangiwe.exception;

public class InsufficientBalanceException extends WithdrawalException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}