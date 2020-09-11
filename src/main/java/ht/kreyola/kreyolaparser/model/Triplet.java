package ht.kreyola.kreyolaparser.model;

public class Triplet {
    private String subject;
    private String verb;
    private String complement;

    public Triplet(String subject, String verb, String complement) {
        this.subject = subject;
        this.verb = verb;
        this.complement = complement;
    }

    public String getSubject() {
        return subject;
    }

    public String getVerb() {
        return verb;
    }

    public String getComplement() {
        return complement;
    }
}
