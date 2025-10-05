package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Pizza> pizzas = new ArrayList<>();

    public Carta() { }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}

