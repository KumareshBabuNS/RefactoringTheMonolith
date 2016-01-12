package com.monolitospizza.controllers;

import com.monolitospizza.model.*;
import com.monolitospizza.services.BasePizzaMenuOptions;
import com.monolitospizza.services.MenuService;
import com.monolitospizza.services.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Matt Stine
 */
public class OrderControllerTest {

    private MenuService mockMenuService;
    private OrderController orderController;
    private OrderService mockOrderService;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        mockOrderService = mock(OrderService.class);
        mockMenuService = mock(MenuService.class);

        orderController = new OrderController(mockOrderService, mockMenuService);
        customer = new Customer("Finn", "fn2187@firstorder.net", "+1(999)999-2187");
    }

    @Test
    public void startsANewPickupOrder() {
        Order order = new Order(OrderType.FOR_PICKUP, customer);
        when(mockOrderService.startNewPickupOrder(1L)).thenReturn(order);
        ModelMap modelMap = new ModelMap();

        String view = orderController.startNewPickupOrder(1L, modelMap);

        assertThat(view, is(equalTo("addAPizza")));
        assertThat(modelMap.get("currentOrder"), is(equalTo(order)));
    }

    @Test
    public void startsANewDeliveryOrder() {
        customer.setAddress(new Address("2187 Jakku Ave.", "Jakku", "CA", "92187"));
        Order order = new Order(OrderType.FOR_DELIVERY, customer);
        when(mockOrderService.startNewDeliveryOrder(1L)).thenReturn(order);
        ModelMap modelMap = new ModelMap();

        String view = orderController.startNewDeliveryOrder(1L, modelMap);

        assertThat(view, is(equalTo("addAPizza")));
        assertThat(modelMap.get("currentOrder"), is(equalTo(order)));
    }

    @Test
    public void shouldLoadBaseOptionsForNewPizza() {
        BasePizzaMenuOptions basePizzaMenuOptions = new BasePizzaMenuOptions(Arrays.asList(new Size("Large", BigDecimal.ZERO)),
                Arrays.asList(new Crust("Thin")),
                Arrays.asList(new Sauce("Normal")));
        when(mockMenuService.loadBasePizzaMenuOptions())
                .thenReturn(basePizzaMenuOptions);
        ModelMap modelMap = new ModelMap();

        String view = orderController.startNewPizza(modelMap);

        verify(mockMenuService).loadBasePizzaMenuOptions();
        assertThat(view, is(equalTo("chooseBaseOptions")));
        assertThat(modelMap.get("basePizzaMenuOptions"), is(equalTo(basePizzaMenuOptions)));
    }

}
