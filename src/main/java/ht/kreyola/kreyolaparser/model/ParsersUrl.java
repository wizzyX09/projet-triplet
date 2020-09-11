package ht.kreyola.kreyolaparser.model;

public enum ParsersUrl {
    FRENCH("edu/stanford/nlp/models/lexparser/frenchFactored.ser.gz");

    private final String v;

    ParsersUrl(String value) {
        this.v = value;
    }

    public String value() {
        return this.v;
    }
}
