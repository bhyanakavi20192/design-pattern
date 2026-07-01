package com.example.patterns.model;

public enum PatternCategory {
    CREATIONAL("Creational", "How objects are created"),
    STRUCTURAL("Structural", "How objects and classes are composed"),
    BEHAVIORAL("Behavioral", "How objects communicate and divide responsibility"),
    SPRING_ARCHITECTURE("Spring Architecture", "How real Spring systems stay reliable");

    private final String displayName;
    private final String description;

    PatternCategory(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
