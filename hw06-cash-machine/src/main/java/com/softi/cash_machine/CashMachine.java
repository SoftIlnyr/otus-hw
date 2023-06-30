package com.softi.cash_machine;

public interface CashMachine {

    void putMoney(BillBundle billBundle);

    BillBundle getMoney(Integer amount);

    Integer getBalance();
}
