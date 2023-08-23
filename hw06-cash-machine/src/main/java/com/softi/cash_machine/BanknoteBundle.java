package com.softi.cash_machine;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

public class BanknoteBundle {

    @Getter
    Map<BanknoteType, Integer> banknotes;

    public BanknoteBundle() {
        banknotes = new HashMap<>();
        for (BanknoteType banknoteType : BanknoteType.values()) {
            banknotes.put(banknoteType, 0);
        }
    }

    public BanknoteBundle(BanknoteBundle banknoteBundle) {
        this.banknotes = new HashMap<>(banknoteBundle.getBanknotes());
    }
    
    public int getSum() {
        int result = 0;
        for (BanknoteType banknoteType : banknotes.keySet()) {
            result += banknoteType.getValue() * banknotes.get(banknoteType);
        }
        return result;
    }

    public void addBanknotes(BanknoteType banknoteType, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Значение должно быть больше нуля");
        }
        banknotes.put(banknoteType, banknotes.get(banknoteType) + amount);
    }
    
}
