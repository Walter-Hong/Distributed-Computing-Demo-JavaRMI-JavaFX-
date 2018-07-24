package sample.view.model;

import javafx.beans.property.*;
import sample.entity.Customer;

import java.time.LocalDate;

public class CustomerProperty {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final StringProperty phone;
    private final IntegerProperty postalCode;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> birthday;


    public CustomerProperty(Customer customer) {
        this.id = new SimpleIntegerProperty(customer.getId());
        this.firstName = new SimpleStringProperty(customer.getFirstName());
        this.lastName = new SimpleStringProperty(customer.getLastName());
        this.street = new SimpleStringProperty(customer.getStreet());
        this.phone = new SimpleStringProperty(customer.getPhone());
        this.postalCode = new SimpleIntegerProperty(customer.getPostalCode());
        this.city = new SimpleStringProperty(customer.getCity());
        this.birthday = new SimpleObjectProperty<>(customer.getBirthday());
    }

    public Customer toCustomer() {
        return new Customer(getId(), getFirstName(), getLastName(), getStreet(), getPhone(), getPostalCode(), getCity(), getBirthday());
    }

    public CustomerProperty() {
        this.id = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.postalCode = new SimpleIntegerProperty();
        this.city = new SimpleStringProperty();
        this.birthday = new SimpleObjectProperty<>();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
