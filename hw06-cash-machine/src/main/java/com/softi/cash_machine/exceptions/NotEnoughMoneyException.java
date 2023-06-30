package com.softi.cash_machine.exceptions;

public class NotEnoughMoneyException extends RuntimeException{

    private final String MESSAGE = "Недостаточно средств на счете!";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
