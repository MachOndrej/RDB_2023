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
public class Oddeleni_Telefon {
    private String nazev, cislo;

    public Oddeleni_Telefon(String nazev, String cislo) {
        this.nazev = nazev;
        this.cislo = cislo;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getCislo() {
        return cislo;
    }

    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    @Override
    public String toString() {
        return "Oddeleni_Telefon{" + "nazev=" + nazev + ", cislo=" + cislo + '}';
    }
    
    
}
