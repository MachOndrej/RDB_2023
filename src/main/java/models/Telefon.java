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
public class Telefon {
    private String cislo;

    public Telefon(String cislo) {
        this.cislo = cislo;
    }

    public String getCislo() {
        return cislo;
    }

    public void setCislo(String cislo) {
        this.cislo = cislo;
    }

    @Override
    public String toString() {
        return "Telefon{" + "cislo=" + cislo + '}';
    }
}
