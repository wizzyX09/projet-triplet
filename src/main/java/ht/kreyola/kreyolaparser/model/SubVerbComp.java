package ht.kreyola.kreyolaparser.model;

import java.util.ArrayList;
import java.util.List;

public class SubVerbComp {
    private String subject;
    private List<String> verbs;
    private List<String> comps;

    public SubVerbComp(String subject, List<String> verbs, List<String> comps) {
        this.subject = subject;
        this.verbs = verbs;
        this.comps = comps;
    }
    public SubVerbComp(String subject) {
        this.subject = subject;
        this.verbs = new ArrayList<>();
        this.comps = new ArrayList<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getVerbs() {
        return verbs;
    }

    public void setVerbs(List<String> verbs) {
        this.verbs = verbs;
    }

    public List<String> getComps() {
        return comps;
    }

    public void setComps(List<String> comps) {
        this.comps.addAll(comps);
    }

    public void  addComp(String comp){
        comps.add(comp);
    }

    public void  addVerbs(String verb){
        verbs.add(verb);
    }

    @Override
    public String toString() {
        return "SubVerbComp{" +
                "subject='" + subject + '\'' +
                ", verb='" + verbs + '\'' +
                ", comps=" + comps +
                '}';
    }
}
