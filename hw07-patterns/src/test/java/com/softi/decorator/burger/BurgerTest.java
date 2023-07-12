package com.softi.decorator.burger;

import com.softi.decorator.burger.Burger;
import com.softi.decorator.burger.ClassicBurger;
import com.softi.decorator.burger.ExtraCheeseDecorator;
import com.softi.decorator.burger.ExtraCucumberDecorator;
import com.softi.decorator.burger.ExtraCutletDecorator;
import com.softi.decorator.burger.Wopper;
import org.junit.jupiter.api.Test;

class BurgerTest {
    
    @Test
    public void testGetCost() {
        Burger burger = new ClassicBurger();

        burger = new ExtraCheeseDecorator(burger);
        burger = new ExtraCheeseDecorator(burger);
        burger = new ExtraCucumberDecorator(burger);
        burger = new ExtraCutletDecorator(burger);
        burger = new ExtraCutletDecorator(burger);

        System.out.println(burger.getDescription());
        System.out.println(burger.getCost());

        Burger wopper = new Wopper();
        wopper = new ExtraCheeseDecorator(wopper);
        wopper = new ExtraCutletDecorator(wopper);
        wopper = new ExtraCutletDecorator(wopper);
        wopper = new ExtraCucumberDecorator(wopper);

        System.out.println(wopper.getDescription());
        System.out.println(wopper.getCost());
        
        
        
    }

}