package database_communication;
import configs.SQLConfig;
import models.*;
//přidat importy tabulek
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bson.Document;

public class DatabaseManager {
    final private SQLServerDataSource dataSource;
    
    public DatabaseManager() throws SQLException {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName(SQLConfig.SERVER_NAME);
        dataSource.setPortNumber(SQLConfig.PORT);
        dataSource.setDatabaseName(SQLConfig.DATABASE_NAME);
        dataSource.setUser(SQLConfig.USERNAME);
        dataSource.setPassword(SQLConfig.PASSWORD);
    }

    public void insertMeasurementToMongo(HodnotyMereni hodnotyMereni){
        MongoDBManager mongoDBManager = new MongoDBManager();
        Document document = new Document("place", hodnotyMereni.getPlace())
                .append("datetime", hodnotyMereni.getDateTime())
                .append("temperature", hodnotyMereni.getTemperature())
                .append("pressure", hodnotyMereni.getPressure())
                .append("precipitation", hodnotyMereni.getPrecipation());
        mongoDBManager.insertDocument(document);
        mongoDBManager.closeConnection();
    }

    public void insertMeasurementToMongo(ArrayList<HodnotyMereni> hodnotyMereniArrayList){
        MongoDBManager mongoDBManager = new MongoDBManager();
        for (HodnotyMereni hodnotyMereni:
             hodnotyMereniArrayList) {
            Document document = new Document("place", hodnotyMereni.getPlace())
                    .append("datetime", hodnotyMereni.getDateTime())
                    .append("temperature", hodnotyMereni.getTemperature())
                    .append("pressure", hodnotyMereni.getPressure())
                    .append("precipitation", hodnotyMereni.getPrecipation());
            mongoDBManager.insertDocument(document);
        }
        mongoDBManager.closeConnection();
    }

    public boolean insertToAutomaticke_mereni(Automaticke_mereni automaticke_mereni) throws SQLException {
        if (!getAutomatickeMereniPrimaryKey(automaticke_mereni).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Automaticke_mereni(misto,od,do,regnum) VALUES(?,?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, automaticke_mereni.getMisto());
        p.setString(2, String.valueOf(automaticke_mereni.getOd()));
        p.setString(3, String.valueOf(automaticke_mereni.getDo()));
        p.setString(4, automaticke_mereni.getRegnum());

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }
    
    public ArrayList<Automaticke_mereni> getAutomatickeMereniPrimaryKey(Automaticke_mereni automaticke_mereni) throws SQLException{
        ArrayList<Automaticke_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, regnum FROM Automaticke_mereni WHERE misto=? AND od=? AND do=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, automaticke_mereni.getMisto());
        p.setString(2, String.valueOf(automaticke_mereni.getOd()));
        p.setString(3, String.valueOf(automaticke_mereni.getDo()));
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Automaticke_mereni(r.getString("misto"), r.getInt("od"), r.getInt("do"), r.getString("regnum")));
        }
        return data;
    }

    public ArrayList<Automaticke_mereni> getAutomatickeMereniPrimaryKey(String misto, int od, int _do) throws SQLException{
        ArrayList<Automaticke_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, regnum FROM Automaticke_mereni WHERE misto=? AND od=? AND do=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, misto);
        p.setString(2, String.valueOf(od));
        p.setString(3, String.valueOf(_do));
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Automaticke_mereni(r.getString("misto"), r.getInt("od"), r.getInt("do"), r.getString("regnum")));
        }
        return data;
    }

    public ArrayList<Automaticke_mereni> getAutomatickeMereniByPlace(String place) throws SQLException{
        ArrayList<Automaticke_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, regnum FROM Automaticke_mereni WHERE misto=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, place);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Automaticke_mereni(r.getString("misto"), r.getInt("od"), r.getInt("do"), r.getString("regnum")));
        }
        return data;
    }

    public boolean insertToManualni_mereni(Manualni_mereni manualni_mereni) throws SQLServerException, SQLException{
        if (!getManualniMereniPrimaryKey(manualni_mereni).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Manualni_mereni(misto, od, do, osoba_email, nazev_oddeleni) VALUES(?,?,?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, manualni_mereni.getMisto());
        p.setString(2, String.valueOf(manualni_mereni.getOd()));
        p.setString(3, String.valueOf(manualni_mereni.getDo()));
        p.setString(4, manualni_mereni.getOsoba_email());
        p.setString(5, manualni_mereni.getNazev_oddeleni());

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }

    public ArrayList<Manualni_mereni> getAllManualni_mereni() throws SQLException, SQLException{
        ArrayList<Manualni_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, osoba_email, nazev_oddeleni FROM Manualni_mereni";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Manualni_mereni(r.getString("misto"), r.getLong("od"), r.getLong("do"),
                    r.getString("osoba_email"), r.getString("nazev_oddeleni")));
        }
        c.close();
        return data;
    }
    
    public ArrayList<Manualni_mereni> getManualniMereniPrimaryKey(Manualni_mereni manualni_mereni) throws SQLException{
        ArrayList<Manualni_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, osoba_email, nazev_oddeleni FROM Manualni_mereni WHERE misto=? AND od=? AND do=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, manualni_mereni.getMisto());
        p.setString(2, String.valueOf(manualni_mereni.getOd()));
        p.setString(3, String.valueOf(manualni_mereni.getDo()));
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Manualni_mereni(r.getString("misto"), r.getLong("od"), r.getLong("do"),
                    r.getString("osoba_email"), r.getString("nazev_oddeleni")));
        }
        return data;
    }

    public ArrayList<Manualni_mereni> getManualniMereniByPlace(String place) throws SQLException{
        ArrayList<Manualni_mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do, osoba_email, nazev_oddeleni FROM Manualni_mereni WHERE misto=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, place);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Manualni_mereni(r.getString("misto"), r.getLong("od"), r.getLong("do"),
                    r.getString("osoba_email"), r.getString("nazev_oddeleni")));
        }
        return data;
    }

    public boolean insertToMereni(Mereni mereni) throws SQLException {
        if (!getMereniPrimaryKey(mereni).isEmpty()) {
            return false;
        }       
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Mereni(misto,od,do) VALUES(?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, mereni.getMisto());
        p.setString(2, String.valueOf(mereni.getOd()));
        p.setString(3, String.valueOf(mereni.getDo()));

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }
    
    public ArrayList<Mereni> getAllMereni() throws SQLException {
        ArrayList<Mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do FROM Mereni";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Mereni(r.getString("misto"), r.getLong("od"), r.getLong("do")));
        }
        c.close();
        return data;
    }
    
    public ArrayList<Mereni> getMereniPrimaryKey(Mereni mereni) throws SQLException{
        ArrayList<Mereni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT misto, od, do FROM Mereni WHERE misto=? AND od=? AND do=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, mereni.getMisto());
        p.setString(2, String.valueOf(mereni.getOd()));
        p.setString(3, String.valueOf(mereni.getDo()));
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Mereni(r.getString("misto"), r.getInt("od"), r.getInt("do")));
        }
        return data;
    }

    public boolean insertToOddeleni(Oddeleni oddeleni) throws SQLServerException, SQLException{
        if (!getOddeleniPrimaryKey(oddeleni).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Oddeleni(nazev, ulice, cislo_popisne, mesto) VALUES(?,?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, oddeleni.getNazev());
        p.setString(2, oddeleni.getUlice());
        p.setString(3, oddeleni.getCislo_popisne());
        p.setString(4, oddeleni.getMesto());

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }

    public ArrayList<Oddeleni> getAllOddeleni() throws SQLException, SQLException{
        ArrayList<Oddeleni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT nazev, ulice, cislo_popisne, mesto FROM Oddeleni";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Oddeleni(r.getString("nazev"), r.getString("ulice"),
                    r.getString("cislo_popisne"), r.getString("mesto")));
        }
        c.close();
        return data;
    }
    
    public ArrayList<Oddeleni> getOddeleniPrimaryKey(Oddeleni oddeleni) throws SQLException{
        ArrayList<Oddeleni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT nazev, ulice, cislo_popisne, mesto FROM Oddeleni WHERE nazev=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, oddeleni.getNazev());
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Oddeleni(r.getString("nazev"), r.getString("ulice"), r.getString("cislo_popisne"), r.getString("mesto")));
        }
        return data;
    }

    public ArrayList<Oddeleni> getOddeleniPrimaryKey(String nazev) throws SQLException{
        ArrayList<Oddeleni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT nazev, ulice, cislo_popisne, mesto FROM Oddeleni WHERE nazev=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, nazev);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Oddeleni(r.getString("nazev"), r.getString("ulice"), r.getString("cislo_popisne"), r.getString("mesto")));
        }
        return data;
    }

    public boolean insertToOddeleni_Telefon(Oddeleni_Telefon oddeleni_telefon) throws SQLServerException, SQLException{
        if (!getOddeleniTelefonPrimaryKey(oddeleni_telefon).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Oddeleni_Telefon(nazev, cislo) VALUES(?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, oddeleni_telefon.getNazev());
        p.setString(2, oddeleni_telefon.getCislo());

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }

    public ArrayList<Oddeleni_Telefon> getAllOddeleni_Telefon() throws SQLException, SQLException{
        ArrayList<Oddeleni_Telefon> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT nazev, cislo FROM Oddeleni_Telefon";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Oddeleni_Telefon(r.getString("nazev"), r.getString("cislo")));
        }
        c.close();
        return data;
    }
    
    public ArrayList<Oddeleni_Telefon> getOddeleniTelefonPrimaryKey(Oddeleni_Telefon oddeleni_telefon) throws SQLException{
        ArrayList<Oddeleni_Telefon> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT nazev, cislo FROM Oddeleni_Telefon WHERE nazev=? AND cislo=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, oddeleni_telefon.getNazev());
        p.setString(2, oddeleni_telefon.getCislo());
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Oddeleni_Telefon(r.getString("nazev"), r.getString("cislo")));
        }
        return data;
    }

    public boolean insertToOsoba(Osoba osoba) throws SQLServerException, SQLException{
        if (!getOsobaPrimaryKey(osoba).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Osoba(jmeno, prijmeni, email) VALUES(?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, osoba.getJmeno());
        p.setString(2, osoba.getPrijmeni());
        p.setString(3, osoba.getEmail());

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }
    
    public ArrayList<Osoba> getAllOsoba() throws SQLException, SQLException{
        ArrayList<Osoba> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT jmeno, prijmeni, email FROM Osoba";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Osoba(r.getString("jmeno"), r.getString("prijmeni"), r.getString("email")));
        }
        c.close();
        return data;
    }
    
    public ArrayList<Osoba> getOsobaPrimaryKey(Osoba osoba) throws SQLException{
        ArrayList<Osoba> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT jmeno, prijmeni, email FROM Osoba WHERE email=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, osoba.getEmail());
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Osoba(r.getString("jmeno"), r.getString("prijmeni"), r.getString("email")));
        }
        return data;
    }

    public ArrayList<Osoba> getOsobaPrimaryKey(String email) throws SQLException{
        ArrayList<Osoba> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT jmeno, prijmeni, email FROM Osoba WHERE email=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, email);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Osoba(r.getString("jmeno"), r.getString("prijmeni"), r.getString("email")));
        }
        return data;
    }

    public boolean insertToTelefon(Telefon telefon) throws SQLServerException, SQLException{
        if (!getTelefonPrimaryKey(telefon).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Telefon(cislo) VALUES(?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, telefon.getCislo().replace("+420", "").substring(0, 9));

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }

    public ArrayList<Telefon> getAllTelefon() throws SQLException, SQLException{
        ArrayList<Telefon> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT cislo FROM Telefon";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Telefon(r.getString("telefon")));
        }
        c.close();
        return data;
    }

    public ArrayList<Telefon> getAllTelefonFromOddeleni(String oddeleni_nazev) throws SQLException{
        ArrayList<Telefon> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT cislo FROM Oddeleni_Telefon WHERE nazev=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, oddeleni_nazev);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Telefon(r.getString("cislo")));
        }
        return data;
    }

    public ArrayList<Telefon> getTelefonPrimaryKey(Telefon telefon) throws SQLException{
        ArrayList<Telefon> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT cislo FROM Telefon WHERE cislo=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, telefon.getCislo());
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Telefon(r.getString("cislo")));
        }
        return data;
    }

    public boolean insertToZarizeni(Zarizeni zarizeni) throws SQLServerException, SQLException{
        if (!getZarizeniPrimaryKey(zarizeni).isEmpty()) {
            return false;
        } 
        Connection c = dataSource.getConnection();
        String sql = "INSERT INTO Zarizeni(title, manufacturer, regnum, voltage, delta) VALUES(?,?,?,?,?)";
        PreparedStatement p = c.prepareStatement(sql);

        p.setString(1, zarizeni.getTitle());
        p.setString(2, zarizeni.getManufacturer());
        p.setString(3, zarizeni.getRegnum());
        p.setString(4, zarizeni.getVoltage());
        p.setString(5, String.valueOf(zarizeni.getDelta()));

        int n = p.executeUpdate();

        c.close();

        return n > 0;
    }

    public ArrayList<Zarizeni> getAllZarizeni() throws SQLException, SQLException{
        ArrayList<Zarizeni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT title, manufacturer, regnum, voltage, delta FROM Zarizeni";
        PreparedStatement p = c.prepareStatement(sql);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Zarizeni(r.getString("title"), r.getString("manufacturer"),
                    r.getString("regnum"), r.getString("voltage"), r.getInt("delta")));
        }
        c.close();
        return data;
    }

    public ArrayList<Zarizeni> getZarizeniPrimaryKey(Zarizeni zarizeni) throws SQLException{
        ArrayList<Zarizeni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT title, delta, manufacturer, voltage, regnum FROM Zarizeni WHERE regnum=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, zarizeni.getRegnum());
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Zarizeni(r.getString("title"), r.getString("manufacturer"), r.getString("voltage"), r.getString("regnum"), r.getInt("delta")));
        }
        return data;
    }

    public ArrayList<Zarizeni> getZarizeniPrimaryKey(String regnum) throws SQLException{
        ArrayList<Zarizeni> data = new ArrayList<>();
        Connection c = dataSource.getConnection();
        String sql = "SELECT title, delta, manufacturer, voltage, regnum FROM Zarizeni WHERE regnum=?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, regnum);
        ResultSet r = p.executeQuery();
        while(r.next()) {
            data.add(new Zarizeni(r.getString("title"), r.getString("manufacturer"), r.getString("voltage"), r.getString("regnum"), r.getInt("delta")));
        }
        return data;
    }

    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        DatabaseManager d = new DatabaseManager();

        //System.out.println(d.insertToMereni(new Mereni("knedlik2", 5143655, 5545454)));

        System.out.println(d.getAllManualni_mereni());

        //System.out.println(d.insertToZarizeni(new Zarizeni("Measure 1000", "kdoVI", "CZ1254", "[12, 24]", 1)));
        //System.out.println(d.getAllZarizeni());

//        d.insertMeasurementToMongo(new HodnotyMereni("knedlik", 456456, 0.5, 1000, 58));

//        System.out.println(d.insertToAutomaticke_mereni(new Automaticke_mereni("knedlik2", 5143655, 5545454, "CZ1254")));
//        System.out.println(d.getAllAutomaticke_mereni());
    }

}
