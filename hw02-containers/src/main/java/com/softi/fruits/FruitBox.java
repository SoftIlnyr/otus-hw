package com.softi.fruits;

import java.util.ArrayList;

public class FruitBox<T extends Fruit>{

    private ArrayList<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
    
    public int weight() {
        int weight = fruits.stream()
                .mapToInt(Fruit::getWeight)
                .sum();

        return weight;
    }

    public void pourInto(FruitBox<? super T> fruitBox) {
        fruitBox.fruits.addAll(this.fruits);
        this.fruits = new ArrayList<>();
    }

    public boolean compare(FruitBox<? extends Fruit> o) {
        return this.weight() == o.weight();
    }
}
