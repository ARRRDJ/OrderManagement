package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by robert on 4/20/16.
 */
public class Company {
    //    TODO: validations
    @Id
    private String id;
    private String name;
    private String invoiceEmail;
    private String address;
    private String zipcode;
    private String city;
    private String mailBox;
    private String number;

    @DBRef
    private List<ContactPerson> contactPersons;

    public Company() {
    }

    public Company(String name, String invoiceEmail, String city, String zipcode, String address, List<ContactPerson> contactPersons, String mailBox, String number) {
        this.name = name;
        this.invoiceEmail = invoiceEmail;
        this.city = city;
        this.zipcode = zipcode;
        this.address = address;
        this.contactPersons = contactPersons;
        this.mailBox = mailBox;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoiceEmail() {
        return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        this.invoiceEmail = invoiceEmail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(List<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public String getMailBox() {
        return mailBox;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
