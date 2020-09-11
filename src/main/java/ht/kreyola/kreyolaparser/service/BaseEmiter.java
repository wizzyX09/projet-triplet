package ht.kreyola.kreyolaparser.service;

import edu.stanford.nlp.ling.TaggedWord;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.SentenceStatus;
import ht.kreyola.kreyolaparser.model.Tag;
import ht.kreyola.kreyolaparser.model.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

public class BaseEmiter implements TripletEmiter {

    private String subject(RichSentence richSentence) {
        String firstNoun = null;
        String verb = null;
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            String tag = taggedWord.tag();
            if (Tag.NC.name().equals(tag) || Tag.NPP.name().equals(tag) || Tag.N.name().equals(tag) ) {
                firstNoun = taggedWord.word();
                break;
            }
            if (Tag.V.name().equals(tag)) {
                verb = taggedWord.word();
                break;
            }
        }
        if (firstNoun != null)
            return firstNoun;
        else {
            String[] words = verb != null ? verb.split("\\s+") : new String[0];
            if (words.length > 1)
                return words[0];
        }
        return null;
    }

    private String verb(RichSentence richSentence) {
        String verb = "";
        int firstVerbIndex = 0;
        for (int i = 0; i < richSentence.getTaggedWords().size(); i++) {
            TaggedWord taggedWord = richSentence.getTaggedWords().get(i);
            if (Tag.V.name().equals(taggedWord.tag())) {
                verb = taggedWord.word();
                firstVerbIndex = i;
                break;
            }
        }
       if(firstVerbIndex + 1 < richSentence.getTaggedWords().size()) {
           if(richSentence.getTaggedWords().get(firstVerbIndex+1).tag().equals(Tag.V.name())) {
               return verb.concat(richSentence.getTaggedWords().get(firstVerbIndex+1).word());
           }
       }
        if(firstVerbIndex + 2 < richSentence.getTaggedWords().size()) {
            if(richSentence.getTaggedWords().get(firstVerbIndex+2).tag().equals(Tag.V.name())) {
                return verb.concat(richSentence.getTaggedWords().get(firstVerbIndex+2).word());
            }
        }
       return verb;
    }

    private String complement(RichSentence richSentence) {
        boolean verbFound = false;
        List<String> nouns = new ArrayList<>();
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            if (Tag.V.name().equals(taggedWord.tag()))
                verbFound = true;
            if (verbFound && taggedWord.tag().equals(Tag.NC.name()))
                nouns.add(taggedWord.word());
        }
        return String.join(" ", nouns);
    }

    @Override
    public List<Triplet> emit(RichSentence richSentence) {
        String subject = subject(richSentence);
        String verb = verb(richSentence);
        String complement = complement(richSentence);
        if (!isEmpty(subject) && !isEmpty(verb) && !isEmpty(complement)) {
            richSentence.setStatus(SentenceStatus.PROCESSED);
            return Collections.singletonList(new Triplet(subject, verb, complement));
        }
        return Collections.emptyList();
    }

    // TODO
    // et TOUSSAINT
    // plusieurs verbes dans une phrase Wislet
    // taille fonctionnelle (nombre de triplet) Toussaint
    // entree/sortie lecture/ecriture Wislet
    // identifie les phrases incorrectes Toussaint ou Wislet
}
