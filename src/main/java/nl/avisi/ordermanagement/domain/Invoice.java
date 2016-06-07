package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Sam on 1-6-2016.
 */
public class Invoice {
    @Id
    private String id;
    private String number;
    private Date scheduledDate;
    private Date sendAt;
    private Date expireDate;

    public Invoice(String number, Date scheduledDate, Date sendAt, Date expireDate) {
        this.number = number;
        this.scheduledDate = scheduledDate;
        this.sendAt = sendAt;
        this.expireDate = expireDate;
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

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }
}
