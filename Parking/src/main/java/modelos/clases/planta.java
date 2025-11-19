package modelos.clases;

public class planta {

    private int idPlanta;
    private String nombrePlaza;
    private int numPlazas;
    private boolean esVip;

    // Getters y Setters
    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getNombrePlaza() {
        return nombrePlaza;
    }

    public void setNombrePlaza(String nombrePlaza) {
        this.nombrePlaza = nombrePlaza;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public boolean isEsVip() {
        return esVip;
    }

    public void setEsVip(boolean esVip) {
        this.esVip = esVip;
    }
}
