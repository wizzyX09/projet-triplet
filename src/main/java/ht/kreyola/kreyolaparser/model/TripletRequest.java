package ht.kreyola.kreyolaparser.model;


import ht.kreyola.kreyolaparser.contants.Language;

import java.util.List;

public class TripletRequest {
    Language useCaseLanguage;
    List<RichSentence> sentences;

    public TripletRequest(Language useCaseLanguage, List<RichSentence> sentences) {
        this.useCaseLanguage = useCaseLanguage;
        this.sentences = sentences;
    }

    public Language getUseCaseLanguage() {
        return useCaseLanguage;
    }

    public void setUseCaseLanguage(Language useCaseLanguage) {
        this.useCaseLanguage = useCaseLanguage;
    }

    public List<RichSentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<RichSentence> sentences) {
        this.sentences = sentences;
    }
}
