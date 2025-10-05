package model;

import java.io.Serializable;

public class Ingrediente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private String proveedor;
    private String nombreProveedor;
    private boolean gluten;
    private boolean lacteos;
    private boolean huevos;
    private boolean picante;

    public Ingrediente(String id, String nombre, String proveedor, String nombreProveedor,
                       boolean gluten, boolean lacteos, boolean huevos, boolean picante) {
        this.id = id;
        this.nombre = nombre;
        this.proveedor = proveedor;
        this.nombreProveedor = nombreProveedor;
        this.gluten = gluten;
        this.lacteos = lacteos;
        this.huevos = huevos;
        this.picante = picante;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }

    public String getNombreProveedor() { return nombreProveedor; }
    public void setNombreProveedor(String nombreProveedor) { this.nombreProveedor = nombreProveedor; }

    public boolean isGluten() { return gluten; }
    public void setGluten(boolean gluten) { this.gluten = gluten; }

    public boolean isLacteos() { return lacteos; }
    public void setLacteos(boolean lacteos) { this.lacteos = lacteos; }

    public boolean isHuevos() { return huevos; }
    public void setHuevos(boolean huevos) { this.huevos = huevos; }

    public boolean isPicante() { return picante; }
    public void setPicante(boolean picante) { this.picante = picante; }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", proveedor='" + proveedor + '\'' +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", gluten=" + (gluten ? "SI" : "NO") +
                ", lacteos=" + (lacteos ? "SI" : "NO") +
                ", huevos=" + (huevos ? "SI" : "NO") +
                ", picante=" + (picante ? "SI" : "NO") +
                '}';
    }
}

