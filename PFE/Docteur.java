package com.reydev.tuto.androiddatabase;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Docteur implements Comparable<Docteur>, Serializable {

    // new Docteur(DocName, DocFix, DocAddress, DocCity, DocCountry, DocLatitude, DocLongitude);
    //-------------------------------------------------------------------------------------

    // DocName
    private String DocName;

    // DocSpecialite
    private String DocSpecialite;
    // DocFix
    private String DocFix;

    // DocAddress
    private String DocAddress;

    // DocCity
    private String DocCity;

    // DocCountry
    private String DocCountry;

    // DocLatitude
    private double DocLatitude;

    // DocLongitude
    private double DocLongitude;

    // Distance : Distance Between Current User's Device Location and the Docteurs.
    private double DocDistance;

    /**
     * Docteur Constructor.
     *
     * @param DocName      Docteur's Name.
     * @param specialite   Docteur's Spécialité
     * @param DocFix       Docteur's Fix phone Number.
     * @param DocAddress   Docteur's Address.
     * @param DocCity      Docteur's City.
     * @param DocCountry   Docteur's Country.
     * @param DocLatitude  Docteur's Latitude   for Geo localisation.
     * @param DocLongitude Docteur's Longitude  for Geo localisation.
     * @param DocDistance  Docteur's Distance From Current User Location
     */

    public Docteur(String DocName, String specialite ,String DocFix, String DocAddress, String DocCity, String DocCountry, double DocLatitude, double DocLongitude, @Nullable double DocDistance) {
        this.DocName = DocName;
        this.DocSpecialite = specialite;
        this.DocFix = DocFix;
        this.DocAddress = DocAddress;
        this.DocCity = DocCity;
        this.DocCountry = DocCountry;
        this.DocLatitude = DocLatitude;
        this.DocLongitude = DocLongitude;
        this.DocDistance = DocDistance;
    }

    /**
     * @return Docaramacie's Name.
     */
    public String getDocName() {
        return DocName;
    }

    /**
     * @return Docaramacie's Specialite Phone Number.
     */
    public String getDocSpecialite() {
        return DocSpecialite;
    }

    /**
     * @return Docaramacie's Fix Phone Number.
     */
    public String getDocFix() {
        return DocFix;
    }

    /**
     * @return Docaramacie's Address.
     */
    public String getDocAddress() {
        return DocAddress;
    }

    /**
     * @return Docaramacie's City.
     */
    public String getDocCity() {
        return DocCity;
    }

    /**
     * @return Docaramacie's Country.
     */
    public String getDocCountry() {
        return DocCountry;
    }

    /**
     * @return Docaramacie's Latitude   for Geo localisation.
     */
    public double getDocLatitude() {
        return DocLatitude;
    }

    /**
     * @return Docaramacie's Longitude  for Geo localisation.
     */
    public double getDocLongitude() {
        return DocLongitude;
    }

    /**
     * @return Docaramacie's Longitude  for Geo localisation.
     */
    public double getDocDistance() {
        return DocDistance;
    }

    @Override
    public int compareTo(Docteur Docteur) {
        if (Docteur.DocDistance == 0) {
            return DocName.compareTo(Docteur.DocName);
        } else {
            if (DocDistance == Docteur.DocDistance)
                return 0;
            else if (DocDistance > Docteur.DocDistance)
                return 1;
            else
                return -1;
        }
    }
}