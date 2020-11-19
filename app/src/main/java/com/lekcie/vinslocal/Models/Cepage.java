package com.lekcie.vinslocal.Models;

public class Cepage {


    private int idCepage;
    private String nomCepage;
    private int idCorrVincepage;
    private int idVin;


    public String getNomCepage() {
        return nomCepage;
    }

    public void setNomCepage(String nomCepage) {
        this.nomCepage = nomCepage;
    }

    public int getIdCepage() {
        return idCepage;
    }

    public void setIdCepage(int idCepage) {
        this.idCepage = idCepage;
    }



    public int getIdCorrVincepage() {
        return idCorrVincepage;
    }

    public void setIdCorrVincepage(int idCorrVincepage) {
        this.idCorrVincepage = idCorrVincepage;
    }

    public int getIdVin() {
        return idVin;
    }

    public void setIdVin(int idVin) {
        this.idVin = idVin;
    }


    @Override
    public String toString() {
        return "Cepage{" +
                "idCepage=" + idCepage +
                ", nomCepage='" + nomCepage + '\'' +
                ", idCorrVincepage=" + idCorrVincepage +
                ", idVin=" + idVin +
                '}';
    }
}
