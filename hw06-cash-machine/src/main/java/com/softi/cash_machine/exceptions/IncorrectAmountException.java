package com.softi.cash_machine.exceptions;

public class IncorrectAmountException extends RuntimeException {

    private final String MESSAGE = "Невозможно выдать запрашиваемую сумму! Нет таких Купюр.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
