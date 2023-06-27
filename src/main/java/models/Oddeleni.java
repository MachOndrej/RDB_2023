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
public class Oddeleni {
    private String nazev, ulice, cislo_popisne, mesto;

    public Oddeleni(String nazev, String ulice, String cislo_popisne, String mesto) {
        this.nazev = nazev;
        this.ulice = ulice;
        this.cislo_popisne = cislo_popisne;
        this.mesto = mesto;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getUlice() {
        return ulice;
    }

    public void setUlice(String ulice) {
        this.ulice = ulice;
    }

    public String getCislo_popisne() {
        return cislo_popisne;
    }

    public void setCislo_popisne(String cislo_popisne) {
        this.cislo_popisne = cislo_popisne;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    @Override
    public String toString() {
        return "Oddeleni{" + "nazev=" + nazev + ", ulice=" + ulice + ", cislo_popisne=" + cislo_popisne + ", mesto=" + mesto + '}';
    }
}
