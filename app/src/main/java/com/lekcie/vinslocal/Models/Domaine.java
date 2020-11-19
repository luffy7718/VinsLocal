package com.lekcie.vinslocal.Models;



public class Domaine  {

   private int idDomaine;
    private String nomDomaine;
    private double latitude;
    private double longitude;
    private String idMarker;
    private String telephone;
    private String adresse;
    private  String horaires;
    private Vins vins;
    private PhotosDomaines photoDomaines;




    public PhotosDomaines getPhotoDomaines() {
        return photoDomaines;
    }

    public void setPhotoDomaines(PhotosDomaines photoDomaines) {
        this.photoDomaines = photoDomaines;
    }

    public Vins getVins() {
        return vins;
    }

    public void setVins(Vins vins) {
        this.vins = vins;
    }

    public String getIdMarker() {
        return idMarker;
    }

    public void setIdMarker(String idMarker) {
        this.idMarker = idMarker;
    }

    public int getIdDomaine() {
        return idDomaine;
    }

    public void setIdDomaine(int idDomaine) {
        this.idDomaine = idDomaine;
    }

    public String getNomDomaine() {
        return nomDomaine;
    }

    public void setNomDomaine(String nomDomaine) {
        this.nomDomaine = nomDomaine;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }






    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getHoraires() {
        return horaires;
    }

    public void setHoraires(String horaires) {
        this.horaires = horaires;
    }



    @Override
    public String toString() {
        return "Domaine{" +
                "idDomaine=" + idDomaine +
                ", nomDomaine='" + nomDomaine + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", idMarker='" + idMarker + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adresse='" + adresse + '\'' +
                ", horaires='" + horaires + '\'' +
                ", vins=" + vins +
                ", photoDomaines=" + photoDomaines +
                '}';
    }
}



