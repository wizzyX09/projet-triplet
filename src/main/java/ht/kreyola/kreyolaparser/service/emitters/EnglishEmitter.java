package ht.kreyola.kreyolaparser.service.emitters;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import ht.kreyola.kreyolaparser.contants.EnglishTag;
import ht.kreyola.kreyolaparser.model.*;

import java.util.*;

import static ht.kreyola.kreyolaparser.util.RandomHelpers.uuid;
import static ht.kreyola.kreyolaparser.util.TripletHelpers.getOperationType;
import static java.util.Collections.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.toList;

public class EnglishEmitter implements TripletEmiter {

    private String subject(RichSentence richSentence) {
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            String tag = taggedWord.tag();
            if (EnglishTag.NN.name().equals(tag)) {
                return taggedWord.word();
            }
        }
        return null;
    }

    private String verb(RichSentence richSentence) {
        for (TaggedWord taggedWord : richSentence.getTaggedWords()) {
            if (EnglishTag.VBZ.name().equals(taggedWord.tag())) {
                return taggedWord.word();
            }
        }

        return null;
    }


    private String complement(RichSentence richSentence) {
        List<String> words = richSentence
                .getTaggedWords()
                .stream()
                .filter(taggedWord -> EnglishTag.NN.name().equals(taggedWord.tag()))
                .map(Word::word)
                .collect(toList());

        if (words.size() > 0)
            return words.get(words.size() - 1);
        else
            return null;
    }


    @Override
    public List<Triplet> emit(RichSentence richSentence) {
        final String subject = subject(richSentence);
        final String verb = verb(richSentence);
        final String complement = complement(richSentence);
        if (nonNull(subject) && nonNull(verb) && nonNull(complement)) {
            return singletonList(new Triplet(uuid(), subject, verb, complement, getOperationType(verb)));
        } else {
            return emptyList();
        }
    }
}
