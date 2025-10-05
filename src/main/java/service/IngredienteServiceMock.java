package service;

import model.Ingrediente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredienteServiceMock implements IngredienteService {

    private List<Ingrediente> ingredientes;

    public IngredienteServiceMock() {
        this.ingredientes = new ArrayList<>();
        this.inicializarDatosMock();
    }

    private void inicializarDatosMock() {
        // Solo 2 ingredientes iniciales como solicitaste
        Ingrediente i1 = new Ingrediente("ING001","Mozzarella", "PROV001", "Quesos Artesanales", false, true, false, false);
        Ingrediente i2 = new Ingrediente ("ING002","Pepperoni", "PROV002", "Carnicería Donatello", false, false, false, true);
        try {
            this.actualizarIngrediente(i1);
            this.actualizarIngrediente(i2);
        } catch (Exception e){
            System.out.println("Error inicializando");
        }
    }

    @Override
    public List<Ingrediente> listadoIngredientes() throws IOException, ClassNotFoundException {
        return this.ingredientes;
    }

    @Override
    public void actualizarIngrediente(Ingrediente ing) throws IOException, ClassNotFoundException {
        this.ingredientes.add(ing);
    }
}
