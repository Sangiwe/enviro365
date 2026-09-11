package com.enviro.assessment.junior.sangiwe.exception;

// Base type for all withdrawal rule violations. Having a common parent means
// the global exception handler can catch WithdrawalException generically later
// if needed, while each subclass still carries its own specific meaning.
public abstract class WithdrawalException extends RuntimeException {
    public WithdrawalException(String message) {
        super(message);
    }
}