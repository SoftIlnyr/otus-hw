package com.softi.cash_machine;

import java.math.BigDecimal;

public interface CashMachine {

    void putMoney(BillBundle billBundle);

    void putMoney(BillType billType, Integer amount);

    BillBundle getMoney(BigDecimal amount);

    BigDecimal getBalance();
}
