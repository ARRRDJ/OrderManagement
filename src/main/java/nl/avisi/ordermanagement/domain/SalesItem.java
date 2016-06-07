package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created by robert on 4/20/16.
 */
public class SalesItem {

    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String sku;
    @DBRef
    private Type type;
    @DBRef
    private Tax tax;

    public SalesItem() {
    }

    @PersistenceConstructor
    public SalesItem(String name, String description, double price, String sku, Type type, Tax tax) {
        this.name = name;
        this.description = description;
        this.tax = tax;
        this.sku = sku;
        this.price = price;
        this.type = type;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}