package ht.kreyola.kreyolaparser.model;

import ht.kreyola.kreyolaparser.contants.OperationType;

public class Triplet {
    private String id;
    private String subject;
    private String verb;
    private String complement;
    private OperationType operationType;
    private boolean include = true;

    public Triplet(String id, String subject, String verb, String complement, OperationType operationType) {
        this.id = id;
        this.subject = subject;
        this.verb = verb;
        this.complement = complement;
        this.operationType = operationType;
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

    public OperationType getOperationType() {
        return operationType;
    }

    public String getId() {
        return id;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }
}
