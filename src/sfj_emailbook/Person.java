package sfj_emailbook;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty id;
    private final SimpleStringProperty deposit;
    private final SimpleIntegerProperty d2;

    public Person() {
        this.id = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");       
        this.deposit = new SimpleStringProperty("");
        this.d2 = new SimpleIntegerProperty(0);
    }


    public Person(String fName, String lName, String mail, String fdeposit, Integer fd2) {
        this.id = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(mail);        
        this.deposit = new SimpleStringProperty(String.valueOf(Integer.parseInt(fdeposit) * 2));
        this.d2 = new SimpleIntegerProperty(fd2);
    }

    Person(Integer id, String lName, String fName, String mail, String fdeposit, Integer fd2) {
        this.id = new SimpleStringProperty(String.valueOf(id));
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(mail);       
        this.deposit = new SimpleStringProperty(String.valueOf(fdeposit));
        this.d2 = new SimpleIntegerProperty(fd2);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLasName(String lName) {
        lastName.set(lName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String mail) {
        email.set(mail);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String fId) {
        id.set(fId);
    }

    public String getDeposit() {
        return deposit.get();
    }

    public void setDeposit(String fdeposit) {
        id.set(fdeposit);
    }

    public Integer getD2() {
        return d2.get();
    }

    public void setD2(Integer fd2) {
        d2.set(fd2);
    }

    /*
    public IntegerProperty d2Property() {
        return d2;
    }*/

}
