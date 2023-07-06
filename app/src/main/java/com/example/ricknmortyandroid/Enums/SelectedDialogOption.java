package com.example.ricknmortyandroid.Enums;

public enum SelectedDialogOption {
    DISPLAYSTYLE("Display Style"),
    SORTSTYLE("Sort Style");

    private final String value;

    SelectedDialogOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String[] getStringArray(){
        SelectedDialogOption[] displayStyles = SelectedDialogOption.values();
        String[] displayStrings = new String[displayStyles.length];

        for (int i = 0; i < displayStyles.length; i++) {
            displayStrings[i] = displayStyles[i].getValue();
        }
        return displayStrings;
    }

    public static SelectedDialogOption fromString(String text) {
        for (SelectedDialogOption selectedDialogOption : SelectedDialogOption.values()) {
            if (selectedDialogOption.value.equalsIgnoreCase(text)) {
                return selectedDialogOption;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
