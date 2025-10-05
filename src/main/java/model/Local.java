package model;

public class Local {
    private String id;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String telefono;
    private int m2;

    // Constructor
    public Local(String id, String nombre, String direccion, String codigoPostal,
                 String poblacion, String provincia, String telefono, int m2) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.telefono = telefono;
        this.m2 = m2;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
    public String getPoblacion() { return poblacion; }
    public void setPoblacion(String poblacion) { this.poblacion = poblacion; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public int getM2() { return m2; }
    public void setM2(int m2) { this.m2 = m2; }

    @Override
    public String toString() {
        return id + "," + nombre + "," + direccion + "," + codigoPostal + "," +
                poblacion + "," + provincia + "," + telefono + "," + m2;
    }
}

