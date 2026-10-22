package com.brewbuddy.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartItem implements Serializable {
    private final String cartItemId;
    private final CoffeeItem coffeeItem;
    private final Size size;
    private final boolean isIced;
    private final boolean hasExtraShot;
    private final boolean hasVanilla;
    private final boolean hasCaramel;
    private final boolean hasWhippedCream;
    private final boolean hasChocolate;
    private int quantity;

    public CartItem(CoffeeItem coffeeItem, Size size, boolean isIced,
                    boolean hasExtraShot, boolean hasVanilla, boolean hasCaramel,
                    boolean hasWhippedCream, boolean hasChocolate, int quantity) {
        this.cartItemId = UUID.randomUUID().toString();
        this.coffeeItem = coffeeItem;
        this.size = size;
        this.isIced = isIced;
        this.hasExtraShot = hasExtraShot;
        this.hasVanilla = hasVanilla;
        this.hasCaramel = hasCaramel;
        this.hasWhippedCream = hasWhippedCream;
        this.hasChocolate = hasChocolate;
        this.quantity = quantity;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public CoffeeItem getCoffeeItem() {
        return coffeeItem;
    }

    public Size getSize() {
        return size;
    }

    public boolean isIced() {
        return isIced;
    }

    public boolean hasExtraShot() {
        return hasExtraShot;
    }

    public boolean hasVanilla() {
        return hasVanilla;
    }

    public boolean hasCaramel() {
        return hasCaramel;
    }

    public boolean hasWhippedCream() {
        return hasWhippedCream;
    }

    public boolean hasChocolate() {
        return hasChocolate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateUnitPrice() {
        double unit = coffeeItem.getBasePrice() + size.getExtraPrice();
        if (hasExtraShot) unit += 1.00;
        if (hasVanilla) unit += 0.50;
        if (hasCaramel) unit += 0.50;
        if (hasWhippedCream) unit += 0.75;
        if (hasChocolate) unit += 0.50;
        return unit;
    }

    public double calculateTotalPrice() {
        return calculateUnitPrice() * quantity;
    }

    public String getCustomizationSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(size.getDisplayName()).append(" • ").append(isIced ? "Iced" : "Hot");
        
        List<String> extras = new ArrayList<>();
        if (hasExtraShot) extras.add("Extra Shot");
        if (hasVanilla) extras.add("Vanilla");
        if (hasCaramel) extras.add("Caramel");
        if (hasWhippedCream) extras.add("Whipped Cream");
        if (hasChocolate) extras.add("Chocolate");

        if (!extras.isEmpty()) {
            sb.append("\nExtras: ");
            for (int i = 0; i < extras.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(extras.get(i));
            }
        }

        return sb.toString();
    }
}
