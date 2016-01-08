package com.monolitospizza.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * @author Matt Stine
 */
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private List<Pizza> pizzas = new ArrayList<>();
    private OrderType type;

    @OneToOne
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;

    public Order(OrderType type, Customer customer) {
        if (type == OrderType.FOR_DELIVERY) {
            notNull(customer.getAddress());
        }

        this.type = type;
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return pizzas
                .stream()
                .map(Pizza::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addPizza(Pizza pizza) {
        this.pizzas.add(pizza);
    }

    public OrderType getType() {
        return type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
        return id;
    }
}
