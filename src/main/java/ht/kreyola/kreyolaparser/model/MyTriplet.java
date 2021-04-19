package ht.kreyola.kreyolaparser.model;

public class MyTriplet {
    private String subject;
    private String verb;
    private String complement;

    public MyTriplet(String subject, String verb, String complement) {
        this.subject = subject;
        this.verb = verb;
        this.complement = complement;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return "MyTriplet{" +
                "subject='" + subject + '\'' +
                ", verb='" + verb + '\'' +
                ", complement='" + complement + '\'' +
                '}';
    }
}
