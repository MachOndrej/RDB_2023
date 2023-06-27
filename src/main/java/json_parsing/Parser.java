package json_parsing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import models.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private JSONObject jsonparser;

    private boolean isAuto;
    private long od;
    private long _do;
    private Automaticke_mereni automaticke_mereni;
    private Manualni_mereni manualni_mereni;
    private Mereni mereni;
    private Oddeleni oddeleni;
    private Oddeleni_Telefon oddeleni_telefon;
    private Osoba osoba;
    private Telefon telefon;
    private Zarizeni zarizeni;
    private ArrayList<HodnotyMereni> hodnotyMerenis;

    public Parser(String json_string, long od, long _do) throws ParseException {
        jsonparser = (JSONObject) new JSONParser().parse(json_string);
        this.od = od;
        this._do = _do;
        hodnotyMerenis = new ArrayList<>();
        parse();
    }

    public Parser(FileReader file, long od, long _do) throws ParseException, IOException {
        jsonparser = (JSONObject) new JSONParser().parse(file);
        this.od = od;
        this._do = _do;
        hodnotyMerenis = new ArrayList<>();
        parse();
    }

    public void parse(){
        String place = (String) jsonparser.get("place");
        mereni = new Mereni(place, od, _do);
        JSONObject type = (JSONObject) jsonparser.get("type");
        if (type.containsKey("auto")){
            this.isAuto = true;
            JSONObject auto = (JSONObject) type.get("auto");
            String title = (String) auto.get("title");
            String delta = (String) auto.get("delta");
            String manufacturer = (String) auto.get("manufacturer");
            JSONArray voltage = (JSONArray) auto.get("voltage");
            String regnum = (String) auto.get("regnum");
            this.zarizeni = new Zarizeni(title, manufacturer, regnum, voltage.toString(), Float.parseFloat(delta));
            this.automaticke_mereni = new Automaticke_mereni(place, od, _do, regnum);
            JSONArray values = (JSONArray) auto.get("values");
            values.forEach(val -> {
                JSONArray single_measurement = (JSONArray) val;
                long datetime = (long) single_measurement.get(0);
                double temp = (double) single_measurement.get(1);
                double pressure = (double) single_measurement.get(2);
                long precipation = (long) single_measurement.get(3) ;
                hodnotyMerenis.add(new HodnotyMereni(place, datetime, temp, pressure, precipation));
            });
        }else{
            this.isAuto = false;
            JSONObject manual = (JSONObject) type.get("manual");
            String name = (String) manual.get("name");
            String surename = (String) manual.get("surename");
            String email = (String) manual.get("email");
            String deptitle = (String) manual.get("deptitle");
            String depphone = (String) manual.get("depphone");
            depphone = depphone.replace("+420", "").substring(0, 9);
            JSONArray depaddress = (JSONArray) manual.get("depaddress");
            String ulice = depaddress.get(0).toString();
            String cislo_popisne = depaddress.get(1).toString();
            String mesto = depaddress.get(2).toString();
            this.manualni_mereni = new Manualni_mereni(place, od, _do, email, deptitle);
            this.osoba = new Osoba(name, surename, email);
            this.oddeleni = new Oddeleni(deptitle, ulice, cislo_popisne, mesto);
            this.oddeleni_telefon = new Oddeleni_Telefon(deptitle, depphone);
            this.telefon = new Telefon(depphone);
            JSONArray values = (JSONArray) manual.get("values");
            values.forEach(val -> {
                JSONArray single_measurement = (JSONArray) val;
                long datetime = (long) single_measurement.get(0);
                double temp = (double) single_measurement.get(1);
                double pressure = (double) single_measurement.get(2);
                long precipation = (long) single_measurement.get(3) ;
                hodnotyMerenis.add(new HodnotyMereni(place, datetime, temp, pressure, precipation));
            });
        }
    }

    public JSONObject getJsonparser() {
        return jsonparser;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public long getOd() {
        return od;
    }

    public long get_do() {
        return _do;
    }

    public Automaticke_mereni getAutomaticke_mereni() {
        return automaticke_mereni;
    }

    public Manualni_mereni getManualni_mereni() {
        return manualni_mereni;
    }

    public Mereni getMereni() {
        return mereni;
    }

    public Oddeleni getOddeleni() {
        return oddeleni;
    }

    public Oddeleni_Telefon getOddeleni_telefon() {
        return oddeleni_telefon;
    }

    public Osoba getOsoba() {
        return osoba;
    }

    public Telefon getTelefon() {
        return telefon;
    }

    public Zarizeni getZarizeni() {
        return zarizeni;
    }

    public ArrayList<HodnotyMereni> getHodnotyMerenis() {
        return hodnotyMerenis;
    }

    public static void main(String args[]) throws ParseException {
        String json = "{\n" +
                "\t\"place\":\"jk\",\n" +
                "\t\t\"type\": {\n" +
                "\t\t\t\"manual\": {\n" +
                "\t\t\t\"name\": \"Petr\",\n" +
                "\t\t\t\"surename\": \"Smrz\",\n" +
                "\t\t\t\"email\": \"petr1@emial.com\",\n" +
                "\t\t\t\"deptitle\": \"eltrom\",\n" +
                "\t\t\t\"depphone\": \"+4206302541542\",\n" +
                "\t\t\t\"depaddress\": [\"Sirkova\",\"24\",\"Brno\"],\n" +
                "\t\t\t\"values\":[\n" +
                "\t\t\t\t[1682992800, -0.9, 1008.8, 2 ],\n" +
                "\t\t\t\t[1682992860, -1.4, 1002.8, 4 ],\n" +
                "\t\t\t\t[1682992920, -1.5, 1003.6, 8 ],\n" +
                "\t\t\t\t[1682992980, -1.7, 1004.0, 10 ],\n" +
                "\t\t\t\t[1682993040, -2.6, 1011.2, 10 ],\n" +
                "\t\t\t\t[1682993100, -8.5, 917.8, 21 ]\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n";
        Parser parser = new Parser(json, 4564, 4564564);

//        String json = "{\n" +
//                "\t\"place\":\"Brno\",\n" +
//                "\t\t\"type\": {\n" +
//                "\t\t\t\"auto\": {\n" +
//                "\t\t\t\"title\": \"Measure 1000\",\n" +
//                "\t\t\t\"delta\": \"1\",\n" +
//                "\t\t\t\"manufacturer\": \"kdoVI\",\n" +
//                "\t\t\t\"voltage\": [12,24],\n" +
//                "\t\t\t\"regnum\": \"CZ1254\",\n" +
//                "\t\t\t\"values\":[\n" +
//                "\t\t\t\t[1682992800, -0.2, 986.4, 9 ],\n" +
//                "\t\t\t\t[1682992860, -1.2, 975.0, 17 ],\n" +
//                "\t\t\t\t[1682999940, 6.8, 863.6, 12 ]\n" +
//                "\t\t\t]\n" +
//                "\t\t}\n" +
//                "\t}\n" +
//                "}\n";
//        try {
//            Parser parser = new Parser(json, 4564, 4564564);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }



    }


}
