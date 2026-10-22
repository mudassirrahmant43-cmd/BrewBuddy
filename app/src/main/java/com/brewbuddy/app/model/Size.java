package com.brewbuddy.app.model;

public enum Size {
    SMALL("Small", 0.00),
    MEDIUM("Medium", 0.50),
    LARGE("Large", 1.00);

    private final String displayName;
    private final double extraPrice;

    Size(String displayName, double extraPrice) {
        this.displayName = displayName;
        this.extraPrice = extraPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getExtraPrice() {
        return extraPrice;
    }
}
