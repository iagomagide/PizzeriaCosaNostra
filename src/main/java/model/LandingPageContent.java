package model;


public class LandingPageContent {
    private String quienesSomos;
    private String amorProductos;
    private String experiencia;

    public LandingPageContent(String quienesSomos, String amorProductos, String experiencia) {
        this.quienesSomos = quienesSomos;
        this.amorProductos = amorProductos;
        this.experiencia = experiencia;
    }

    // Getters
    public String getQuienesSomos() { return quienesSomos; }
    public String getAmorProductos() { return amorProductos; }
    public String getExperiencia() { return experiencia; }
}
