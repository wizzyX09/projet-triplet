package ht.kreyola.kreyolaparser.contants;

public enum ParsersUrl {
    FRENCH("edu/stanford/nlp/models/lexparser/frenchFactored.ser.gz"),
    ENGLISH("edu/stanford/nlp/models/lexparser/englishFactored.ser.gz");

    private final String v;

    ParsersUrl(String value) {
        this.v = value;
    }

    public String value() {
        return this.v;
    }
}
