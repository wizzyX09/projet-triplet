package ht.kreyola.kreyolaparser.contants;

public enum Delimiters {
    PIPE("|"), SPACE("\\\\s+"), DOT("\\.");

    private final String value;

    Delimiters(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
