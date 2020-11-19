package com.lekcie.vinslocal.Models;

public class PhotoVin {

    private int idPhotos;
    private String urlVins;
    private int idVin;



    public int getIdPhotos() {
        return idPhotos;
    }

    public void setIdPhotos(int idPhotos) {
        this.idPhotos = idPhotos;
    }

    public String getUrlVins() {
        return urlVins;
    }

    public void setUrlVins(String urlVins) {
        this.urlVins = urlVins;
    }



    public int getIdVin() {
        return idVin;
    }

    public void setIdVin(int idVin) {
        this.idVin = idVin;
    }



    @Override
    public String toString() {
        return "PhotoVin{" +
                "idPhotos=" + idPhotos +
                ", urlVins='" + urlVins + '\'' +
                ", idVin=" + idVin +'}';
    }
}
