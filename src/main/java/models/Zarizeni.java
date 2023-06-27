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
public class Zarizeni {
    private String title, manufacturer, regnum, voltage;
    private float delta;

    public Zarizeni(String title, String manufacturer, String regnum, String voltage, float delta) {
        this.title = title;
        this.manufacturer = manufacturer;
        this.regnum = regnum;
        this.voltage = voltage;
        this.delta = delta;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    @Override
    public String toString() {
        return "Zarizeni{" + "title=" + title + ", manufacturer=" + manufacturer + ", regnum=" + regnum + ", voltage=" + voltage + ", delta=" + delta + '}';
    }
    
    
    
}
