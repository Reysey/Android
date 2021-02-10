package com.reydev.tuto.androiddatabase;

public class Pharmacy {



    private String nomPharmacie;
    private String codepostalPharmacie;
    private String telPharmacie;
    private String addressPharmacie;

    public Pharmacy(){
    }

    public Pharmacy(String nomPharmacie, String codepostalPharmacie, String telPharmacie, String addressPharmacie) {
        this.nomPharmacie = nomPharmacie;
        this.codepostalPharmacie = codepostalPharmacie;
        this.telPharmacie = telPharmacie;
        this.addressPharmacie = addressPharmacie;
    }

    public String getNomPharmacie() {
        return nomPharmacie;
    }

    public String getCodepostalPharmacie() {
        return codepostalPharmacie;
    }

    public String getTelPharmacie() {
        return telPharmacie;
    }

    public String getAddressPharmacie() {
        return addressPharmacie;
    }


    public void setNomPharmacie(String nomPharmacie) {
        this.nomPharmacie = nomPharmacie;
    }

    public void setCodepostalPharmacie(String codepostalPharmacie) {
        this.codepostalPharmacie = codepostalPharmacie;
    }

    public void setTelPharmacie(String telPharmacie) {
        this.telPharmacie = telPharmacie;
    }

    public void setAddressPharmacie(String addressPharmacie) {
        this.addressPharmacie = addressPharmacie;
    }


}
