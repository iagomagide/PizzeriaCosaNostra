package service;

import model.Pizza;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class PizzaServiceMock implements PizzaService{

    private List<Pizza> lista;

    public PizzaServiceMock(){
        this.lista = new ArrayList<>();
        inicializarDatosMock();
    }

    private void inicializarDatosMock(){
        List<String> l1 = new ArrayList<>();
        l1.add("ING001");
        l1.add("ING002");
        l1.add("ING014");

        Pizza p1 = new Pizza("PZZ001", "Mamma mia", "Clásica pizza con base de tomate, mozzarella, pepperoni y orégano."
                , 850, 9.5, 20, l1 );

        List<String> l2 = new ArrayList<>();
        l2.add("ING001");
        l2.add("ING002");
        l2.add("ING007");

        Pizza p2 = new Pizza("PZZ001", "Barbacoa", "Pizza con salsa barbacoa, pollo, ternera, cebolla y queso mozzarella."
                , 875, 10.5, 22, l2 );

        this.lista.add(p1);
        this.lista.add(p2);

    }

    @Override
    public List<Pizza> listadoPizzas() throws JAXBException {
        return lista;
    }

    @Override
    public void actualizarPizza(Pizza p) throws JAXBException {
        this.lista.add(p);
    }
}
