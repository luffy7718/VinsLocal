package com.lekcie.vinslocal.Models;

public class PhotoDomaine {


    private int idPhotos;
    private String urlDomaine;
    private int idDomaine;


    public int getIdPhotos() {
        return idPhotos;
    }

    public void setIdPhotos(int idPhotos) {
        this.idPhotos = idPhotos;
    }

    public String getUrlDomaine() {
        return urlDomaine;
    }

    public void setUrlDomaine(String urlDomaine) {
        this.urlDomaine = urlDomaine;
    }

    public int getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(int idDomaine) {
        this.idDomaine = idDomaine;
    }


    @Override
    public String toString() {
        return "PhotoDomaine{" +
                "idPhotos=" + idPhotos +
                ", urlDomaine='" + urlDomaine + '\'' +
                ", idDomaine=" + idDomaine +
                '}';
    }
}
