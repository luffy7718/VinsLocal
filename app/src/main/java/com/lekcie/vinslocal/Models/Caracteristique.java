package com.lekcie.vinslocal.Models;

public class Caracteristique {


    private  int idCaract;
    private String nomCaract;
    private int idCorrVinCaract;
    private int idVin;


    public int getIdCaract() {
        return idCaract;
    }

    public void setIdCaract(int idCaract) {
        this.idCaract = idCaract;
    }

    public String getNomCaract() {
        return nomCaract;
    }

    public void setNomCaract(String nomCaract) {
        this.nomCaract = nomCaract;
    }

    public int getIdCorrVinCaract() {
        return idCorrVinCaract;
    }

    public void setIdCorrVinCaract(int idCorrVinCaract) {
        this.idCorrVinCaract = idCorrVinCaract;
    }

    public int getIdVin() {
        return idVin;
    }

    public void setIdVin(int idVin) {
        this.idVin = idVin;
    }

    @Override
    public String toString() {
        return "Caracteristique{" +
                "idCaract=" + idCaract +
                ", nomCaract='" + nomCaract + '\'' +
                ", idCorrVinCaract=" + idCorrVinCaract +
                ", idVin=" + idVin +
                '}';
    }
}
