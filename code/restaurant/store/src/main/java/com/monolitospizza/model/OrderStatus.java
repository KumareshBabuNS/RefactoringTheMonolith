package com.monolitospizza.model;

import java.io.Serializable;

/**
 * @author Matt Stine
 */
public enum OrderStatus implements Serializable {
    RECEIVED("Received"),
    READY_FOR_PICKUP("Ready for Pickup"),
    OUT_FOR_DELIVERY("Out for Delivery");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
