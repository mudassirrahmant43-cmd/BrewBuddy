package com.brewbuddy.app.model;

import java.io.Serializable;

public class CoffeeItem implements Serializable {
    private final String id;
    private final String name;
    private final String description;
    private final double basePrice;
    private final Category category;
    private final int imageResId;
    private final boolean isPopular;
    private final boolean isFeatured;

    public CoffeeItem(String id, String name, String description, double basePrice,
                      Category category, int imageResId, boolean isPopular, boolean isFeatured) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.category = category;
        this.imageResId = imageResId;
        this.isPopular = isPopular;
        this.isFeatured = isFeatured;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Category getCategory() {
        return category;
    }

    public int getImageResId() {
        return imageResId;
    }

    public boolean isPopular() {
        return isPopular;
    }

    public boolean isFeatured() {
        return isFeatured;
    }
}
