package com.softi.cash_machine;

import com.softi.cash_machine.exceptions.ImpossibleToWithdrawSpecifiedAmountException;
import com.softi.cash_machine.exceptions.IncorrectAmountException;
import com.softi.cash_machine.exceptions.NotEnoughMoneyException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CashMachineImpl implements CashMachine {

    private Map<BillType, Integer> billStockMap;

    public CashMachineImpl() {
        billStockMap = new HashMap<>();
        for (BillType bill : BillType.values()) {
            billStockMap.put(bill, 0);
        }
    }

    @Override
    public void putMoney(BillBundle billBundle) {
        for (BillType billType : billBundle.getBillTypes()) {
            Integer updateQuantity = billStockMap.get(billType) + billBundle.getBillAmount(billType);
            billStockMap.put(billType, updateQuantity);
        }
    }

    @Override
    public BillBundle getMoney(Integer amount) {
        BillBundle result = new BillBundle();
        
        Integer currentBalance = getBalance();
        Integer oneHundred = 100;
        Integer hundreds = amount / oneHundred;

        if (amount - hundreds * oneHundred > 0) {
            throw new IncorrectAmountException();
        }
        if (currentBalance < amount) {
            throw new NotEnoughMoneyException();
        }

        Comparator<BillType> billTypeValueComparator = Comparator.comparing(BillType::getValue);

        List<BillType> billTypes = Arrays.stream(BillType.values())
                .sorted(billTypeValueComparator.reversed())
                .collect(Collectors.toList());

        for (BillType billType : billTypes) {
            Integer billValue = billType.getValue();

            if (amount <= billValue) {
                continue;
            }

            Integer numberOfBills = amount / billValue;
            amount -= billValue * numberOfBills;
            result.addBills(billType, numberOfBills);
        }

        if (amount > 0) {
            throw new ImpossibleToWithdrawSpecifiedAmountException();
        }

        for (BillType billType : result.getBillTypes()) {
            int updateCount = billStockMap.get(billType) - result.getBillAmount(billType);
            billStockMap.put(billType, updateCount);
        }

        return result;
    }
    
    @Override
    public Integer getBalance() {
        int balance = 0;
        for (BillType billType : this.billStockMap.keySet()) {
            balance += billType.getValue() * billStockMap.get(billType);
        }
        return balance;
    }
}
