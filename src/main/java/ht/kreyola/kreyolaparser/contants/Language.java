package ht.kreyola.kreyolaparser.contants;


public enum Language {
    FR("FR"), EN("EN");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

