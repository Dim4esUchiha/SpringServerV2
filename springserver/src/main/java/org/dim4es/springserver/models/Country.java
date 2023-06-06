package org.dim4es.springserver.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends AbstractEntity {

    @Column
    private String name;

    @Column
    private String code;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String cityName) {
        this.name = cityName;
    }
}

