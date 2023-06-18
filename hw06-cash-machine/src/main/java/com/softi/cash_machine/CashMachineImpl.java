package com.softi.cash_machine;

import com.softi.cash_machine.exceptions.ImpossibleToWithdrawSpecifiedAmountException;
import com.softi.cash_machine.exceptions.IncorrectAmountException;
import com.softi.cash_machine.exceptions.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CashMachineImpl implements CashMachine {

    private HashMap<BillType, Integer> billStock;

    public CashMachineImpl() {
        this.billStock = new HashMap<>();
        for (BillType bill : BillType.values()) {
            this.billStock.put(bill, 0);
        }
    }

    @Override
    public void putMoney(BillBundle billBundle) {
        for (BillType billType : billBundle.getBillTypes()) {
            putMoney(billType, billBundle.getBillAmount(billType));
        }
    }

    @Override
    public void putMoney(BillType billType, Integer amount) {
        Integer updateQuantity = billStock.get(billType) + amount;
        this.billStock.put(billType, updateQuantity);
    }

    @Override
    public BillBundle getMoney(BigDecimal amount) {
        BillBundle result = new BillBundle();
        
        BigDecimal currentBalance = getBalance();
        BigDecimal oneHundred = BigDecimal.valueOf(100);
        BigDecimal hundreds = amount.divide(oneHundred, RoundingMode.DOWN);
        
        if (amount.subtract(hundreds.multiply(oneHundred)).compareTo(BigDecimal.ZERO) > 0) {
            throw new IncorrectAmountException();
        }
        if (currentBalance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyException();
        }

        Comparator<BillType> billTypeValueComparator = Comparator.comparing(BillType::getValue);

        List<BillType> billTypes = Arrays.stream(BillType.values()).sorted(billTypeValueComparator.reversed())
                .collect(Collectors.toList());

        for (BillType billType : billTypes) {
            BigDecimal billValue = BigDecimal.valueOf(billType.getValue());

            if (amount.compareTo(billValue) <= 0) {
                continue;
            }
            
            BigDecimal numberOfBills = amount.divide(billValue, RoundingMode.DOWN);
            amount = amount.subtract(billValue.multiply(numberOfBills));
            result.addBills(billType, numberOfBills.intValue());
        }

        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            throw new ImpossibleToWithdrawSpecifiedAmountException();
        }

        for (BillType billType : result.getBillTypes()) {
            int updateCount = this.billStock.get(billType) - result.getBillAmount(billType);
            this.billStock.put(billType, updateCount);
        }

        return result;
    }
    
    @Override
    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.ZERO;
        for (BillType billType : this.billStock.keySet()) {
            balance = balance.add(
                    BigDecimal.valueOf(billType.getValue()).multiply(BigDecimal.valueOf(this.billStock.get(billType))));
        }
        return balance;
    }
}
