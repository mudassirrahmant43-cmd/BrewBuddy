package com.brewbuddy.app.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private final String orderId;
    private final String customerName;
    private final String pickupMethod;
    private final String notes;
    private final List<CartItem> items;
    private final double subtotal;
    private final double tax;
    private final double grandTotal;
    private final long timestamp;

    public Order(String orderId, String customerName, String pickupMethod, String notes,
                 List<CartItem> items, double subtotal, double tax, double grandTotal) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.pickupMethod = pickupMethod;
        this.notes = notes;
        this.items = items;
        this.subtotal = subtotal;
        this.tax = tax;
        this.grandTotal = grandTotal;
        this.timestamp = System.currentTimeMillis();
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPickupMethod() {
        return pickupMethod;
    }

    public String getNotes() {
        return notes;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
