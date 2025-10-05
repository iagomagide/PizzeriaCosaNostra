package model;

import java.util.ArrayList;
import java.util.List;


public class Pizza {

    private String id;

    private String nombre;

    private String descripcion;

    private int calorias;

    private double precio;

    private int tiempoPreparacion;

    private List<String> ingredientes = new ArrayList<>();

    public Pizza() { } // JAXB necesita constructor sin parámetros

    public Pizza(String id, String nombre, String descripcion, int calorias, double precio, int tiempoPreparacion, List<String> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.calorias = calorias;
        this.precio = precio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.ingredientes = ingredientes;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCalorias() { return calorias; }
    public void setCalorias(int calorias) { this.calorias = calorias; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getTiempoPreparacion() { return tiempoPreparacion; }
    public void setTiempoPreparacion(int tiempoPreparacion) { this.tiempoPreparacion = tiempoPreparacion; }

    public List<String> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<String> ingredientes) { this.ingredientes = ingredientes; }

    //Adaptador para la vista
    public List<IngredienteRef> getIngredientesRef() {
        List<IngredienteRef> result = new ArrayList<>();
        for (String ingredienteId : getIngredientes()) {
            result.add(new IngredienteRef(ingredienteId));
        }
        return result;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", calorias=" + calorias +
                ", precio=" + precio +
                ", tiempoPreparacion=" + tiempoPreparacion +
                ", ingredientes=" + ingredientes +
                '}';
    }
}