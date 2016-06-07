package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;


/**
 * Created by robert on 4/20/16.
 */
public class Order {
    @Id
    private String id;
    private String number;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
    private String period;
    private String invoiceEmail;
    private int payDays;
    private String identification;
    @DBRef
    private Company company;
    @DBRef
    private ContactPerson contactPerson;
    @DBRef
    private Sender sender;
    private List<OrderLine> orderLines;

    @DBRef
    private List<Invoice> invoices;

    private String message;

    public Order() {

    }

    public Order(String number, boolean active, Date createdAt, Date updatedAt, String period, String invoiceEmail, int payDays, Company company, ContactPerson contactPerson, Sender sender, String message, String identification) {
        this.number = number;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.period = period;
        this.invoiceEmail = invoiceEmail;
        this.payDays = payDays;
        this.company = company;
        this.contactPerson = contactPerson;
        this.sender = sender;
        this.message = message;
        this.identification = identification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInvoiceEmail() {
        return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        this.invoiceEmail = invoiceEmail;
    }

    public int getPayDays() {
        return payDays;
    }

    public void setPayDays(int payDays) {
        this.payDays = payDays;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
