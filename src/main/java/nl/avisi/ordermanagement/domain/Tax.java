package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by robert on 5/26/16.
 */
public class Tax {
    @Id
    private String id;
    private Double rate;

    public Tax(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
