package com.reydev.tuto.androiddatabase;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Pharmacie implements Comparable<Pharmacie>, Serializable {

    // new Pharmacie(PhName, PhFix, PhAddress, PhCity, PhCountry, PhLatitude, PhLongitude);
    //-------------------------------------------------------------------------------------

    // PhName
    private String PhName;

    // PhFix
    private String PhFix;

    // PhAddress
    private String PhAddress;

    // PhCity
    private String PhCity;

    // PhCountry
    private String PhCountry;

    // PhLatitude
    private double PhLatitude;

    // PhLongitude
    private double PhLongitude;

    // Distance : Distance Between Current User's Device Location and the Pharmacies.
    private double PhDistance;

    /**
     * Pharmacie Constructor.
     *
     * @param phName        Pharmacie's Name.
     * @param phFix         Pharmacie's Fix Phone Number.
     * @param phAddress     Pharmacie's Address.
     * @param phCity        Pharmacie's City.
     * @param phCountry     Pharmacie's Country.
     * @param phLatitude    Pharmacie's Latitude   for Geo localisation.
     * @param phLongitude   Pharmacie's Longitude  for Geo localisation.
     * @param phDistance      Pharmacie's Distance From Current User Location
     */

    public Pharmacie(String phName, String phFix, String phAddress, String phCity, String phCountry, double phLatitude, double phLongitude, @Nullable double phDistance) {
        this.PhName = phName;
        this.PhFix = phFix;
        this.PhAddress = phAddress;
        this.PhCity = phCity;
        this.PhCountry = phCountry;
        this.PhLatitude = phLatitude;
        this.PhLongitude = phLongitude;
        this.PhDistance = phDistance;
    }

    /**
     *
     * @return Pharamacie's Name.
     */
    public String getPhName() {
        return PhName;
    }

    /**
     *
     * @return Pharamacie's Fix Phone Number.
     */
    public String getPhFix() {
        return PhFix;
    }

    /**
     *
     * @return Pharamacie's Address.
     */
    public String getPhAddress() {
        return PhAddress;
    }

    /**
     *
     * @return Pharamacie's City.
     */
    public String getPhCity() {
        return PhCity;
    }

    /**
     *
     * @return Pharamacie's Country.
     */
    public String getPhCountry() {
        return PhCountry;
    }

    /**
     *
     * @return Pharamacie's Latitude   for Geo localisation.
     */
    public double getPhLatitude() {
        return PhLatitude;
    }

    /**
     *
     * @return Pharamacie's Longitude  for Geo localisation.
     */
    public double getPhLongitude() {
        return PhLongitude;
    }

    /**
     *
     * @return Pharamacie's Longitude  for Geo localisation.
     */
    public double getPhDistance() {
        return PhDistance;
    }

    @Override
    public int compareTo(Pharmacie pharmacie) {
        if(pharmacie.PhDistance == 0){
            return PhName.compareTo(pharmacie.PhName);
        }else{
            if(PhDistance == pharmacie.PhDistance)
                return 0;
            else if(PhDistance > pharmacie.PhDistance)
                return 1;
            else
                return -1;
        }
    }
}
