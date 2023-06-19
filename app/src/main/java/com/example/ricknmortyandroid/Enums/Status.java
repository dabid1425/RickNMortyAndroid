package com.example.ricknmortyandroid.Enums;

public enum Status {
    DEAD("dead"),
    ALIVE("alive"),
    UNKNOWN("unknown");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
