package com.example.ricknmortyandroid.Enums;

public enum Sort {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown"),
    ASCENDING("Ascending"),
    DESCENDING("Descending"),
    NONE("None");

    private final String value;

    Sort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static String[] getStringArray(){
        Sort[] sortArray = Sort.values();
        String[] sortStrings = new String[sortArray.length];

        for (int i = 0; i < sortArray.length; i++) {
            sortStrings[i] = sortArray[i].getValue();
        }
        return sortStrings;
    }

    public static Sort fromString(String text) {
        for (Sort sort : Sort.values()) {
            if (sort.value.equalsIgnoreCase(text)) {
                return sort;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
