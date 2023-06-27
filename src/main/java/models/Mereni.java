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
public class Mereni {
    private String misto;
    private long od;
    private long _do;

    public Mereni(String misto, long od, long _do) {
        this.misto = misto;
        this.od = od;
        this._do = _do;
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

    @Override
    public String toString() {
        return "Mereni{" + "misto=" + misto + ", od=" + od + ", do=" + _do + '}';
    }
    
    
    
}


