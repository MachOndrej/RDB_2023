/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import database_communication.DatabaseManager;
import database_communication.MongoDBManager;
import json_parsing.Parser;
import models.*;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.Array;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Martin Koňák
 */
public class ApplicationLayer {
    private DatabaseManager databaseManager;
    private MongoDBManager mongoManager;
    private String misto;
    private long od, _do;
    private String path = System.getProperty("user.dir") + File.separator + "generator" + File.separator;
    private Parser parser;
    public ApplicationLayer() throws SQLException {
        databaseManager = new DatabaseManager();
        mongoManager = new MongoDBManager();
    }
    
    private boolean makeInputFile(String misto, long od, long _do) throws IOException {
        this.misto = misto;
        this.od = od;
        this._do = _do;

        File myObj = new File(path + "input.txt");
        myObj.createNewFile();

        FileWriter myWriter = new FileWriter(path + "input.txt");
        myWriter.write(String.format("%s,%d,%d\n", this.misto, this.od, this._do));
        myWriter.close();
        return true;
    }

    private boolean executeExe() throws IOException {
        Runtime.getRuntime().exec(path + "RDBFileGenerator.exe", null, new File(path));
        return true;
    }

    private boolean loadJson() throws IOException, ParseException {
        try {
            FileReader fileReader = new FileReader(path + misto + ".json");
            parser = new Parser(fileReader, od, _do);
            fileReader.close();
            return true;
        } catch (FileNotFoundException e){
            return false;
        }
    }

    private boolean ulozZaznam(){
        try {
            if (!databaseManager.insertToMereni(parser.getMereni())){
                return false;
            }
            databaseManager.insertMeasurementToMongo(parser.getHodnotyMerenis());
            if (parser.isAuto()) {
                ulozAutomatickeMereni();
            } else {
                ulozManualniMereni();
            }
        } catch (SQLException e){
            System.out.printf(e.toString());
            return false;
        }
        return true;
    }

    private void ulozManualniMereni() throws SQLException {
        databaseManager.insertToOsoba(parser.getOsoba());
        databaseManager.insertToOddeleni(parser.getOddeleni());
        databaseManager.insertToManualni_mereni(parser.getManualni_mereni());
        databaseManager.insertToTelefon(parser.getTelefon());
        databaseManager.insertToOddeleni_Telefon(parser.getOddeleni_telefon());
    }

    private void ulozAutomatickeMereni() throws SQLException {
        databaseManager.insertToZarizeni(parser.getZarizeni());
        databaseManager.insertToAutomaticke_mereni(parser.getAutomaticke_mereni());
    }

    public boolean vygenerujMereni(String misto, long od, long _do) throws IOException, ParseException, SQLException, InterruptedException {
        makeInputFile(misto, od, _do);
        executeExe();
        int number_of_tries = 0;
        while(!loadJson()){
            number_of_tries++;
            Thread.sleep(500);
        }
        deleteInputFile();
        return ulozZaznam();
    }

    private void deleteInputFile() {
        File myObj = new File(path + "input.txt");
        myObj.delete();
        myObj = new File(path + misto + ".json");
        myObj.delete();
    }

    public double[] minMax(String place){
        return mongoManager.getMinMaxTempsForPlace(place);
    }

    public ArrayList<Double> teplotyProDveMista(String place1, String place2){ return mongoManager.getSameTempsTwoPlaces(place1, place2); }

    public static void main(String[] args) throws IOException, SQLException, ParseException, InterruptedException {
        ApplicationLayer a = new ApplicationLayer();

        System.out.println(a.vygenerujMereni("fdsffhgfh", 1213150000, 1213152000));
    }

    public String recordsByPlace(String place) throws SQLException{
        StringBuilder vystup = new StringBuilder();

        ArrayList<Manualni_mereni> manualniMerenis;
        ArrayList<Automaticke_mereni> automatickeMerenis;
        manualniMerenis = databaseManager.getManualniMereniByPlace(place);
        automatickeMerenis = databaseManager.getAutomatickeMereniByPlace(place);

        for (Manualni_mereni manualni_mereni:
             manualniMerenis) {
//            najdi osobu
            Osoba osoba = databaseManager.getOsobaPrimaryKey(manualni_mereni.getOsoba_email()).get(0);
//            najdi oddeleni
            Oddeleni oddeleni = databaseManager.getOddeleniPrimaryKey(manualni_mereni.getNazev_oddeleni()).get(0);
//            k oddeleni najdi vsechny telefony ArrayList telefonu
            ArrayList<Telefon> telefons = databaseManager.getAllTelefonFromOddeleni(manualni_mereni.getNazev_oddeleni());
            vystup.append("Misto: ").append(manualni_mereni.getMisto()).append(" Od: ").append(manualni_mereni.getOd()).append(", Do: ").append(manualni_mereni.getDo()).append("\n");
            vystup.append("Osoba: ").append(osoba.getJmeno()).append(" ").append(osoba.getPrijmeni()).append(", ")
                    .append(osoba.getEmail());
            vystup.append("\n");
            vystup.append("Oddeleni: ").append(oddeleni.getNazev()).append(", ").append(oddeleni.getUlice()).append(" ")
                    .append(oddeleni.getCislo_popisne()).append(", ").append(oddeleni.getMesto()).append(" | Telefony: ");
            for (Telefon telefon:
                 telefons) {
                vystup.append(telefon.getCislo()).append(", ");
            }
            vystup.append("\n");
        }
        for (Automaticke_mereni automaticke_mereni:
             automatickeMerenis) {
            Zarizeni zarizeni = databaseManager.getZarizeniPrimaryKey(automaticke_mereni.getRegnum()).get(0);
            vystup.append("Misto: ").append(automaticke_mereni.getMisto()).append(" Od: ").append(automaticke_mereni.getOd()).append(" Do: ").append(automaticke_mereni.getDo()).append("\n");
            vystup.append("Zařízení: ").append(zarizeni.getTitle()).append(" ").append(zarizeni.getDelta()).append(" ").append(zarizeni.getManufacturer()).append(" ").append(zarizeni.getVoltage()).append(" ").append(zarizeni.getRegnum()).append("\n");
            vystup.append("\n");
        }

        return vystup.toString();
    }
    
}
