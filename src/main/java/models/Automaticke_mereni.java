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
public class Automaticke_mereni{
    private String misto;
    private long od;
    private long _do;
    private String regnum;

    public Automaticke_mereni(String misto, long od, long _do, String regnum) {
        this.misto = misto;
        this.od = od;
        this._do = _do;
        this.regnum = regnum;
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

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    @Override
    public String toString() {
        return "Automaticke_mereni{" + "misto=" + misto + ", od=" + od + ", _do=" + _do + ", regnum=" + regnum + '}';
    }
    
}
