package nl.avisi.ordermanagement.domain;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;

/**
 * Created by Sjors on 5/25/16.
 */

public class Sender {

    @Id
    private String id;
    private String name;
    @Email
    private String email;
    private String address;
    private String zipCode;
    private String city;
    private String KVKNumber;
    private String BTWNumber;
    private String BICNumber;
    private String IBANNumber;
    private String website;

    public Sender(String name, String email, String address, String zipCode, String city, String KVKNumber, String BICNumber, String IBANNumber, String BTWNumber, String website) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.KVKNumber = KVKNumber;
        this.BTWNumber = BTWNumber;
        this.BICNumber = BICNumber;
        this.IBANNumber = IBANNumber;
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBICNumber() {
        return BICNumber;
    }

    public void setBICNumber(String BICNumber) {
        this.BICNumber = BICNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKVKNumber() {
        return KVKNumber;
    }

    public void setKVKNumber(String KVKNumber) {
        this.KVKNumber = KVKNumber;
    }

    public String getBTWNumber() {
        return BTWNumber;
    }

    public void setBTWNumber(String BTWNumber) {
        this.BTWNumber = BTWNumber;
    }

    public String getIBANNumber() {
        return IBANNumber;
    }

    public void setIBANNumber(String IBANNumber) {
        this.IBANNumber = IBANNumber;
    }
}