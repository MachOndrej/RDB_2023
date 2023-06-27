/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Martin Koňák
 */
public class Manualni_mereni {
    private String misto;
    private long od;
    private long _do;
    private String osoba_email;
    private String nazev_oddeleni;

    public Manualni_mereni(String misto, long od, long _do, String osoba_email, String nazev_oddeleni) {
        this.misto = misto;
        this.od = od;
        this._do = _do;
        this.osoba_email = osoba_email;
        this.nazev_oddeleni = nazev_oddeleni;
    }

    public String getMisto() {
        return misto;
    }

    public void setMisto(String misto) {
        this.misto = misto;
    }

    public long getOd() {
        return od;
    }

    public void setOd(long od) {
        this.od = od;
    }

    public long getDo() {
        return _do;
    }

    public void setDo(long _do) {
        this._do = _do;
    }

    public String getOsoba_email() {
        return osoba_email;
    }

    public void setOsoba_email(String osoba_email) {
        this.osoba_email = osoba_email;
    }

    public String getNazev_oddeleni() {
        return nazev_oddeleni;
    }

    public void setNazev_oddeleni(String nazev_oddeleni) {
        this.nazev_oddeleni = nazev_oddeleni;
    }

    @Override
    public String toString() {
        return "Manualni_mereni{" + "misto=" + misto + ", od=" + od + ", _do=" + _do + ", osoba_email=" + osoba_email + ", nazev_oddeleni=" + nazev_oddeleni + '}';
    }
    
    
    
}
