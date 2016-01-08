package com.monolitospizza.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Matt Stine
 */
public class OrderTest {

    private Order order;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("Rey", "rey@theresistance.com", "+1(999)999-9999");

        Address address = new Address(
                "875 Howard St.",
                "San Francisco",
                "CA",
                "94101"
        );
        customer.setAddress(address);

        order = new Order(OrderType.FOR_DELIVERY, customer);
    }

    @Test
    public void orderCanBeForDelivery() {
        assertThat(order.getType(), equalTo(OrderType.FOR_DELIVERY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deliveryOrderMustHaveCustomerWithAddress() {
        customer = new Customer("Rey", "rey@theresistance.com", "+1(999)999-9999");
        order = new Order(OrderType.FOR_DELIVERY, customer);
    }

    @Test
    public void orderCanBeForPickup() {
        order = new Order(OrderType.FOR_PICKUP, customer);

        assertThat(order.getType(), equalTo(OrderType.FOR_PICKUP));
    }

    @Test
    public void emptyOrderHasNoPrice() {
        assertThat(BigDecimal.ZERO, equalTo(order.getPrice()));
    }

    @Test
    public void orderWithOnePizzaHasPriceOfPizza() {
        Pizza pizza = new Pizza();

        order.addPizza(pizza);

        assertThat(pizza.getPrice(), equalTo(order.getPrice()));
    }

    @Test
    public void orderWithTwoPizzasHasSumOfTheirPrices() {
        Pizza firstPizza = new Pizza(Size.MEDIUM);
        firstPizza.addTopping(Topping.BEEF);
        firstPizza.addTopping(Topping.BACON);

        Pizza secondPizza = new Pizza(Size.SMALL);
        secondPizza.addTopping(Topping.PEPPERONI);
        secondPizza.addTopping(Topping.MUSHROOM);

        BigDecimal expectedPrice = firstPizza
                .getPrice().add(secondPizza.getPrice());

        order.addPizza(firstPizza);
        order.addPizza(secondPizza);

        assertThat(order.getPrice(), equalTo(expectedPrice));
    }

    @Test
    public void orderMustHaveACustomer() {
        assertThat(order.getCustomer(), is(notNullValue()));
    }
}
