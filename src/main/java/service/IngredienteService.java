package service;

import model.Ingrediente;

import java.io.IOException;
import java.util.List;

public interface IngredienteService {

    public List<Ingrediente> listadoIngredientes() throws IOException, ClassNotFoundException;

    public void actualizarIngrediente(Ingrediente ing) throws IOException, ClassNotFoundException;
}
