package com.softi.cash_machine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BillBundle {

    private Map<BillType, Integer> billTypeMap = new HashMap<>();
    
    public void addBills(BillType billType, Integer amount) {
        if (!billTypeMap.containsKey(billType)) {
            billTypeMap.put(billType, 0);
        }
        Integer updateValue = billTypeMap.get(billType) + amount;
        billTypeMap.put(billType, updateValue);
    }

    public Integer getBillAmount(BillType billType) {
        return this.billTypeMap.get(billType);
    }
    
    public Set<BillType> getBillTypes() {
        return this.billTypeMap.keySet();
    }
}
