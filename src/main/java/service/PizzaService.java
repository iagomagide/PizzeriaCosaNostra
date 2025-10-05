package service;

import model.Pizza;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface PizzaService {

    public List<Pizza> listadoPizzas() throws JAXBException;

    public void actualizarPizza(Pizza p) throws JAXBException;

}
