package com.example.ricknmortyandroid.Enums;

public enum Gender {
    FEMALE("Female"),
    MALE("Male"),
    GENDERLESS("Genderless"),
    UNKNOWN("Unknown");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender fromString(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.value.equalsIgnoreCase(text)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}

