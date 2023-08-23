package com.softi.cash_machine;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import lombok.Setter;

public class BanknoteServiceImpl implements BanknoteService {

    @Setter
    private Map<BanknoteType, Integer> banknoteCells;
    @Setter
    private BanknoteSearchService banknoteSearchService;

    public BanknoteServiceImpl() {
        banknoteCells = new TreeMap<>(Comparator.comparing(BanknoteType::getValue).reversed());
        for (BanknoteType banknoteType : BanknoteType.values()) {
            banknoteCells.put(banknoteType, 0);
        }
    }

    @Override
    public int putMoney(Map<BanknoteType, Integer> banknotes) {
        int sum = 0;

        for (BanknoteType banknoteType : banknotes.keySet()) {
            sum += banknoteType.getValue() * banknotes.get(banknoteType);
            banknoteCells.put(banknoteType, banknoteCells.get(banknoteType) + banknotes.get(banknoteType));
        }

        return sum;
    }

    @Override
    public Map<BanknoteType, Integer> getBanknotes(int sum) {

        Map<BanknoteType, Integer> availableVariant = banknoteSearchService.searchBanknoteBundle(banknoteCells, sum);

        if (availableVariant.isEmpty()) {
            throw new IllegalStateException("Невозможно выдать данную сумму");
        }

        return availableVariant;
    }
}
