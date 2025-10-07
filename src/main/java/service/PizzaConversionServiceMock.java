package service;

public class PizzaConversionServiceMock implements PizzaConversionService{

    /*Este servicio debe de tener 2 DAOS:
        a) DAO para leer las pizzas en formato XML y recuperar una lista de Pizzas
        b) DAO de conversion para recibir una lista de Pizzas y generar un JSON
    */
    @Override
    public boolean generarFicheroPizzas() {
        return false;
    }
}
