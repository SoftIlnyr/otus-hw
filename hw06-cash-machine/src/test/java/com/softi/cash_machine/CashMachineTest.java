package com.softi.cash_machine;

import com.softi.cash_machine.exceptions.ImpossibleToWithdrawSpecifiedAmountException;
import com.softi.cash_machine.exceptions.IncorrectAmountException;
import com.softi.cash_machine.exceptions.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class CashMachineTest {

    @ParameterizedTest
    @MethodSource("putMoney")
    void putMoney(BillBundle billBundle, Integer expectedResult) {
        CashMachineImpl cashMachine = new CashMachineImpl();

        cashMachine.putMoney(billBundle);

        assertEquals(BigDecimal.valueOf(expectedResult), cashMachine.getBalance());
    }
    
    static Stream<Arguments> putMoney() {
        BillBundle billBundle1 = new BillBundle();
        billBundle1.addBills(BillType.RUB_100, 1);
        Integer expectedResult1 = 100;

        BillBundle billBundle2 = new BillBundle();
        billBundle2.addBills(BillType.RUB_100, 1);
        billBundle2.addBills(BillType.RUB_500, 1);
        Integer expectedResult2 = 600;

        BillBundle billBundle3 = new BillBundle();
        billBundle3.addBills(BillType.RUB_100, 1);
        billBundle3.addBills(BillType.RUB_500, 1);
        billBundle3.addBills(BillType.RUB_1000, 1);
        Integer expectedResult3 = 1600;

        BillBundle billBundle4 = new BillBundle();
        billBundle4.addBills(BillType.RUB_100, 1);
        billBundle4.addBills(BillType.RUB_500, 1);
        billBundle4.addBills(BillType.RUB_1000, 1);
        billBundle4.addBills(BillType.RUB_5000, 1);
        Integer expectedResult4 = 6600;

        BillBundle billBundle5 = new BillBundle();
        billBundle5.addBills(BillType.RUB_100, 5);
        billBundle5.addBills(BillType.RUB_5000, 1);
        Integer expectedResult5 = 5500;
        
        return Stream.of(
                Arguments.of(billBundle1, expectedResult1),
                Arguments.of(billBundle2, expectedResult2),
                Arguments.of(billBundle3, expectedResult3),
                Arguments.of(billBundle4, expectedResult4),
                Arguments.of(billBundle5, expectedResult5)
        );
    }
    
    @Test
    void getMoney() {
        CashMachineImpl cashMachine = new CashMachineImpl();

        BillBundle billBundle = new BillBundle();
        billBundle.addBills(BillType.RUB_1000, 10);
        cashMachine.putMoney(billBundle);

        BigDecimal balanceBeforeWithdraw = cashMachine.getBalance();

        BigDecimal amount = BigDecimal.valueOf(1000);
        cashMachine.getMoney(amount);

        BigDecimal balanceAfterWithdraw = cashMachine.getBalance();
        
        assertEquals(amount, balanceBeforeWithdraw.subtract(balanceAfterWithdraw));
    }

    @Test
    void getMoney_ImpossibleToWithdrawSpecifiedAmountException() {
        CashMachineImpl cashMachine = new CashMachineImpl();

        BillBundle billBundle = new BillBundle();
        billBundle.addBills(BillType.RUB_1000, 10);
        cashMachine.putMoney(billBundle);

        assertThrows(ImpossibleToWithdrawSpecifiedAmountException.class,
                () -> cashMachine.getMoney(BigDecimal.valueOf(600)));

        System.out.println(cashMachine.getBalance());
    }
    
    @Test
    void getMoney_IncorrectAmountException() {
        CashMachineImpl cashMachine = new CashMachineImpl();

        BillBundle billBundle = new BillBundle();
        billBundle.addBills(BillType.RUB_100, 5);
        cashMachine.putMoney(billBundle);

        assertThrows(IncorrectAmountException.class,
                () -> cashMachine.getMoney(BigDecimal.valueOf(101)));
    }

    @Test
    void getMoney_NotEnoughtMoneyException() {
        CashMachineImpl cashMachine = new CashMachineImpl();

        BillBundle billBundle = new BillBundle();
        billBundle.addBills(BillType.RUB_100, 5);
        cashMachine.putMoney(billBundle);

        assertThrows(NotEnoughMoneyException.class,
                () -> cashMachine.getMoney(BigDecimal.valueOf(600)));
    }
}