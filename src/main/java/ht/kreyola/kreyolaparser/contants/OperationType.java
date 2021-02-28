package ht.kreyola.kreyolaparser.contants;


public enum OperationType {
    IN("IN"), OUT("OUT"), WRITE("WRITE"), READ("READ"), UNKNOWN("UNKNOWN");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

