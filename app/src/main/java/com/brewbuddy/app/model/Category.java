package com.brewbuddy.app.model;

public enum Category {
    ALL("All Coffee"),
    HOT("Hot Coffee"),
    ICED("Iced Coffee"),
    SPECIALTY("Specialty");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
