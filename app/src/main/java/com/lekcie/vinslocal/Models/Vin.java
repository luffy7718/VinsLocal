package com.lekcie.vinslocal.Models;

public class Vin {

    private int idVins;
    private String nomVin;
    private double prix;
    private  String couleur;
    private int millesime;
    private String autreInfos;
    private String recommande;
    private String recommande_commentaire;

    private int idDomaine;
    private Caracterisques caracteristiques;
    private PhotosVins photosVins;
    private Cepages cepages;




    public Caracterisques getCaracteristiques() {
        return caracteristiques;
    }

    public void setCaracteristiques(Caracterisques caracteristiques) {
        this.caracteristiques = caracteristiques;
    }

    public PhotosVins getPhotosVins() {
        return photosVins;
    }

    public void setPhotosVins(PhotosVins photosVins) {
        this.photosVins = photosVins;
    }

    public Cepages getCepages() {
        return cepages;
    }

    public void setCepages(Cepages cepages) {
        this.cepages = cepages;
    }



    public int getIdVins() {
        return idVins;
    }

    public void setIdVins(int idVins) {
        this.idVins = idVins;
    }

    public String getNomVin() {
        return nomVin;
    }

    public void setNomVin(String nomVin) {
        this.nomVin = nomVin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getMillesime() {
        return millesime;
    }

    public void setMillesime(int millesime) {
        this.millesime = millesime;
    }

    public String getAutreInfos() {
        return autreInfos;
    }

    public void setAutreInfos(String autreInfos) {
        this.autreInfos = autreInfos;
    }


    public int getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(int idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getRecommande() {
        return recommande;
    }

    public void setRecommande(String recommande) {
        this.recommande = recommande;
    }

    public String getRecommande_commentaire() {
        return recommande_commentaire;
    }

    public void setRecommande_commentaire(String recommande_commentaire) {
        this.recommande_commentaire = recommande_commentaire;
    }

    @Override
    public String toString() {
        return "Vin{" +
                "idVins=" + idVins +
                ", nomVin='" + nomVin + '\'' +
                ", prix=" + prix +
                ", couleur='" + couleur + '\'' +
                ", millesime=" + millesime +
                ", autreInfos='" + autreInfos + '\'' +
                ", recommande='" + recommande + '\'' +
                ", recommande_commentaire='" + recommande_commentaire + '\'' +
                ", idDomaine=" + idDomaine;
    }
}
