package nl.avisi.ordermanagement.domain;

import org.springframework.data.annotation.Id;

/**
 * Created by robert on 5/12/16.
 */

public class Type {

    @Id
    private String id;
    private String name;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }
}