package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by robert on 5/12/16.
 */
public class OrderLine {
    @Id
    private String id;
    private double quantity;
    @DBRef
    private SalesItem salesItem;

    public OrderLine() {

    }

    @PersistenceConstructor
    public OrderLine(double quantity, SalesItem salesItem) {
        this.quantity = quantity;
        this.salesItem = salesItem;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public SalesItem getSalesItem() {
        return salesItem;
    }

    public void setSalesItem(SalesItem salesItem) {
        this.salesItem = salesItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
