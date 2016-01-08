package com.monolitospizza.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Matt Stine
 */
@Entity
public class Topping {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal price;
    private String name;

    public Topping(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topping topping = (Topping) o;
        return Objects.equals(name, topping.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
