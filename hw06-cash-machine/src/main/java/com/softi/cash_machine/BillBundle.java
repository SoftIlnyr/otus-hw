package com.softi.cash_machine;

import java.util.HashMap;
import java.util.Set;

public class BillBundle {

    private HashMap<BillType, Integer> bills;
    
    public BillBundle() {
        this.bills = new HashMap<>();
    }

    public void addBills(BillType billType, Integer amount) {
        if (!this.bills.containsKey(billType)) {
            this.bills.put(billType, amount);
            return;
        }
        Integer updateValue = this.bills.get(billType) + amount;
        this.bills.put(billType, updateValue);
    }

    public Integer getBillAmount(BillType billType) {
        return this.bills.get(billType);
    }
    
    public Set<BillType> getBillTypes() {
        return this.bills.keySet();
    }
}
