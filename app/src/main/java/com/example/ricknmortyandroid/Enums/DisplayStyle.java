package com.example.ricknmortyandroid.Enums;

public enum DisplayStyle {
    TABLEVIEW("Table View"),
    COLLECTIONVIEW("Collection View");

    private final String value;

    DisplayStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String[] getStringArray(){
        DisplayStyle[] displayStyles = DisplayStyle.values();
        String[] displayStrings = new String[displayStyles.length];

        for (int i = 0; i < displayStyles.length; i++) {
            displayStrings[i] = displayStyles[i].getValue();
        }
        return displayStrings;
    }

    public static DisplayStyle fromString(String text) {
        for (DisplayStyle displayStyle : DisplayStyle.values()) {
            if (displayStyle.value.equalsIgnoreCase(text)) {
                return displayStyle;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
