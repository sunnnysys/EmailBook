package sfj_emailbook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBase {

    final String URL = "jdbc:derby:dataBase;create=true";

    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DBase() {
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println("" + ex);
        }

        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println("" + ex);
            }
        }

        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println("" + ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if (!rs.next()) {
                createStatement.execute("create table contacts(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),lastname varchar(20), firstname varchar(20), email varchar(30), deposit varchar(30), d2 INT)");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println("" + ex);
        }
    }

    public ArrayList<Person> getAllContacts() {
        String sql = "select * from contacts";
        ArrayList<Person> contacts = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            contacts = new ArrayList<Person>();
            while (rs.next()) {
                Person actualPerson = new Person(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("deposit"), rs.getInt("d2"));
                contacts.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van getAllContacts-nál" + ex);
        }
        return contacts;
    }

    public void addContact(Person person) {
        System.out.println(person.getFirstName());
        System.out.println(person.getLastName());
        System.out.println(person.getEmail());
        System.out.println(Integer.parseInt(person.getDeposit()));
        System.out.println(person.getD2());
        try {
            String sql = "insert into contacts (firstname, lastname, email, deposit, d2) values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, Integer.parseInt(person.getDeposit()));
            preparedStatement.setInt(5, person.getD2());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a kontakt hozzáadásakor." + ex);
        }
    }

    public void updateContact(Person person) {
        try {
            String sql = "update contacts set firstname = ?, lastname = ?, email = ?, deposit = ?, d2 = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(person.getId()));
            preparedStatement.setString(2, person.getFirstName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getEmail());
            preparedStatement.setInt(5, Integer.parseInt(person.getDeposit()));
            preparedStatement.setInt(6, person.getD2());
            preparedStatement.execute();
 
        } catch (SQLException ex) {
            System.out.println("Valami baj van a kontakt módosításakor" + ex);
        }
    }

}
