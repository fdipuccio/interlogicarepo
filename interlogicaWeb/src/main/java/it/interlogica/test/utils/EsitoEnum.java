package it.interlogica.test.utils;

public enum EsitoEnum {
    OK("OK"),
    KO("KO");

    private String value;

    EsitoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}