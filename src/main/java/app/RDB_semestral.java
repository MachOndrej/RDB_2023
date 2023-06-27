package app;

import models.HodnotyMereni;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Martin Koňák
 */
public class RDB_semestral {

    private static ApplicationLayer applicationLayer;

    public static void printMenu(){
        System.out.println("\n+----------------------------------+");
        System.out.println("| Vyberte možnost:                 |");
        System.out.println("|  1 - Vygenerovat nové měření     |");
        System.out.println("|  2 - Stejné teploty pro 2 místa  |");
        System.out.println("|  3 - Tabulka maxim a minim       |");
        System.out.println("|  0 - Ukončit program             |");
        System.out.println("+----------------------------------+");
    }

    public static boolean selectionChoice(int selection){
        switch (selection){
            case 0:
                System.out.println("Konec programu.");
                break;
            case 1:
                createNewMereni();
                System.out.println("Měření uloženo");
                break;
            case 2:
                stejneTeploty();
                break;
            case 3:
                minMax();
                break;
            default:
                System.out.println("Invalidní volba.");
                return false;
        }
        return true;
    }

    private static void stejneTeploty() {
        Scanner sc = new Scanner(System.in);
        String place1, place2;
        try {
            System.out.println("Zadejte název místa měření:");
            place1 = sc.next();
            System.out.println("Zadejte název druhého místa měření:");
            place2 = sc.next();
        } catch (Exception e) {
            sc.next();
            System.out.println("Chyba v zadáváni nazvu místa.");
            return;
        }

        System.out.println("Společné naměřené hodnoty pro místa " + place1 + " a " + place2 + ": ");
        ArrayList<Double> list = applicationLayer.teplotyProDveMista(place1, place2);
        for (double l : list) {
            System.out.print(l + ", ");
        }
        System.out.println();
    }

    private static void minMax() {
        Scanner sc = new Scanner(System.in);
        String place;
        try {
            System.out.println("Zadejte nazev místa měření:");
            place = sc.next();
        } catch (Exception e) {
            sc.next();
            System.out.println("Chyba v zadáváni názvu místa.");
            return;
        }
        double[] hodnoty = applicationLayer.minMax(place);
        System.out.println("Místo: " + place);
        System.out.println("Minimální naměřená teplota: " + hodnoty[0]);
        System.out.println("Maximální naměřená teplota: " + hodnoty[1]);
        System.out.println("\n");
        System.out.println("Měření, která proběhla na tomto místě:");
        try {
            System.out.println(applicationLayer.recordsByPlace(place));
        } catch (SQLException e){
            System.out.println("Chyba při komunikaci s DB.");
        }
    }

    public static void createNewMereni(){
        Scanner sc = new Scanner(System.in);
        String place;
        long od;
        long _do;
        try {
            System.out.println("Zadejte název místa měření:");
            place = sc.next();
        } catch (Exception e) {
            System.out.println("Chyba v zadávání názvu místa.");
            sc.next();
            return;
        }
        try {
            System.out.println("Zadejte počáteční čas měření (UNIX time):");
            od = sc.nextLong();
        } catch (Exception e){
            System.out.println("Chyba v zadávání počátečního času měření.");
            sc.next();
            return;
        }
        try {
            System.out.println("Zadejte konečný čas měření (UNIX time):");
            _do = sc.nextLong();
        } catch (Exception e){
            System.out.println("Chyba v zadávání konečného času měření.");
            sc.next();
            return;
        }

        try {
            applicationLayer.vygenerujMereni(place, od, _do);
        } catch (IOException e) {
            System.out.println("Chyba v načítání souboru.");
        } catch (ParseException e) {
            System.out.println("Chyba v parsování.");
        } catch (SQLException e) {
            System.out.println("Chyba v komunikaci s DB.");
        } catch (InterruptedException e) {
            System.out.println("Chyba v runtime.");
        }
    }

    public static void main(String[] args) {
        try {
            applicationLayer = new ApplicationLayer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Scanner sc = new Scanner(System.in);
        int selection = 10;
        do {
            printMenu();
            try {
                selection = sc.nextInt();
            } catch (InputMismatchException e){
                sc.next();
                System.out.println("Invalidní vstup.");
                continue;
            }
            selectionChoice(selection);
        } while (selection != 0);
    }
    
}
