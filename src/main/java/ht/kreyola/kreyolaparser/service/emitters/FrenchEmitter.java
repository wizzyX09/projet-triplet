package ht.kreyola.kreyolaparser.service.emitters;

import edu.stanford.nlp.ling.TaggedWord;
import ht.kreyola.kreyolaparser.contants.FrenchTag;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.contants.SentenceStatus;
import ht.kreyola.kreyolaparser.model.Triplet;

import java.util.*;

import static ht.kreyola.kreyolaparser.util.RandomHelpers.uuid;
import static ht.kreyola.kreyolaparser.util.TripletHelpers.*;
import static org.springframework.util.StringUtils.isEmpty;

public class FrenchEmitter implements TripletEmiter {

    private String subject(RichSentence richSentence) {
        String firstNoun = null;
        String verb = null;
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            String tag = taggedWord.tag();
            if (FrenchTag.NC.name().equals(tag) || FrenchTag.NPP.name().equals(tag) || FrenchTag.N.name().equals(tag)) {
                firstNoun = taggedWord.word();
                break;
            }
            if (FrenchTag.V.name().equals(tag)) {
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

    private List<String> verb(RichSentence richSentence) {
        String verb = "";
        List<String> verbs = new ArrayList<>();
        int firstVerbIndex = 0;
        for (int i = 0; i < richSentence.getTaggedWords().size(); i++) {
            TaggedWord taggedWord = richSentence.getTaggedWords().get(i);
            if (FrenchTag.V.name().equals(taggedWord.tag())) {
                verb = taggedWord.word();
                firstVerbIndex = i;
                break;
            }
        }

        verbHelper(verb, verbs, firstVerbIndex, richSentence);
        return verbs;
    }

    private String verbInfHelper(String verbInf) {
        String verb;
        int verbInfLength = verbInf.length();
        if (verbInf.substring(verbInfLength - 3).equalsIgnoreCase("yer")) {
            verb = verbInf.substring(0, verbInfLength - 3).concat("ie");
        } else if (verbInf.substring(verbInfLength - 2).equalsIgnoreCase("ir")) {
            verb = verbInf.substring(0, verbInfLength - 1).concat("t");
        } else if (verbInf.substring(verbInfLength - 2).equalsIgnoreCase("re")) {
            verb = verbInf.substring(0, verbInfLength - 2);
        } else if (verbInf.substring(verbInfLength - 2).equalsIgnoreCase("er")) {
            verb = verbInf.substring(0, verbInfLength - 1);
        } else {
            verb = verbInf;
        }
        return verb;

    }

    private List<String> verbHelper(String verb, List<String> verbs, int firstVerbIndex, RichSentence richSentence) {
        int firstIndex = firstVerbIndex + 1;
        int nextIndex = firstVerbIndex + 2;
        if (firstIndex < richSentence.getTaggedWords().size()) {
            TaggedWord taggedWord = richSentence.getTaggedWords().get(firstIndex);
            String tag = taggedWord.tag();
            if (tag.equals(FrenchTag.V.name()) || tag.equals(FrenchTag.VINF.name()) || tag.equals(FrenchTag.VPP.name())) {
                verb = verbInfHelper(richSentence.getTaggedWords().get(firstIndex).word());
            } else if (puncHelper(taggedWord) && !verb.isEmpty()) {
                verbs.add(verb);
                verb = "";
                verbHelper(verb, verbs, firstIndex, richSentence);
                nextIndex++;
            }
        }
        if (nextIndex < richSentence.getTaggedWords().size()) {
            TaggedWord taggedWord = richSentence.getTaggedWords().get(nextIndex);
            String tag = taggedWord.tag();
            if (tag.equals(FrenchTag.V.name()) || tag.equals(FrenchTag.VINF.name()) || tag.equals(FrenchTag.VPP.name())) {
                verb = verbInfHelper(richSentence.getTaggedWords().get(nextIndex).word());
            } else if (puncHelper(taggedWord) && !verb.isEmpty()) {
                verbs.add(verb);
                verb = "";
                verbHelper(verb, verbs, nextIndex, richSentence);
            }
        }

        if (!verb.isEmpty()) verbs.add(verb);

        return verbs;
    }

    private List<String> complement(RichSentence richSentence) {
        boolean verbFound = false;
        List<String> complements = new ArrayList<>();
        List<String> nouns = new ArrayList<>();
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            if (FrenchTag.V.name().equals(taggedWord.tag()))
                verbFound = true;
            if (verbFound && taggedWord.tag().equals(FrenchTag.NC.name()))
                nouns.add(taggedWord.word());
            if (puncHelper(taggedWord) && !nouns.isEmpty()) { //if (FrenchTag.CC.name().equals(taggedWord.tag()) && !nouns.isEmpty()){
                complements.add(String.join(" ", nouns));
                nouns.clear();
            }
        }

        complements.add(String.join(" ", nouns));
        return complements;
    }

    private boolean puncHelper(TaggedWord taggedWord) {
        boolean result = false;
        if (FrenchTag.CC.name().equals(taggedWord.tag()))
            result = true;
        else if (FrenchTag.PUNC.name().equals(taggedWord.tag()) && ",".equals(taggedWord.word())) {
            result = true;
        }
        return result;
    }

    @Override
    public List<Triplet> emit(RichSentence richSentence) {
        String subject = subject(richSentence);
        List<String> verbs = verb(richSentence);
        List<String> complements = complement(richSentence);
        List<Triplet> results = new ArrayList<>();

        if (!isEmpty(subject) && !verbs.isEmpty() && !complements.isEmpty()) {
            richSentence.setStatus(SentenceStatus.PROCESSED);
            for (String verb : verbs) {
                results.add(new Triplet(uuid(), subject, verb, complements.get(0), getOperationType(verb)));
            }
            if (complements.size() > 1) {
                for (String verb : verbs) {
                    results.add(new Triplet(uuid(), subject, verb, complements.get(1), getOperationType(verb)));
                }
            }

            return results;
        }
        return Collections.emptyList();
    }

}
