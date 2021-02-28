package ht.kreyola.kreyolaparser.service.parsers;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import ht.kreyola.kreyolaparser.contants.Delimiters;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import ht.kreyola.kreyolaparser.model.TripletRequest;
import ht.kreyola.kreyolaparser.service.emitters.EnglishEmitter;
import ht.kreyola.kreyolaparser.service.emitters.TripletEmiter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ht.kreyola.kreyolaparser.contants.ParsersUrl.ENGLISH;
import static java.util.stream.Collectors.*;


@Service
public class EnglishParser implements Parser {

    LexicalizedParser lp ;
    List<TripletEmiter> emitters;
    public EnglishParser() {
        this.lp = LexicalizedParser.loadModel(ENGLISH.value());
        this.emitters = tripletEmiters();
    }

    private List<String> toSentences(String useCase) {
        String[] sentences = useCase.split(Delimiters.DOT.value());
        return Arrays.stream(sentences).collect(toList());
    }

    @Override
    public List<Triplet> toTriplets(final TripletRequest tripletRequest) {
        List<RichSentence> sentences = tripletRequest.getSentences();

        List<RichSentence> richSentences = sentences.stream().map(sentence -> {
            List<CoreLabel> rawWords = Sentence.toCoreLabelList(sentence.getWords().toArray(new String[0]));
            Tree parse = lp.apply(rawWords);
            sentence.setTaggedWords(parse.taggedYield());
            return sentence;
        }).collect(toList());

        return richSentences
                .stream()
                .flatMap(richSentence -> emitters.stream().flatMap(tripletEmiter -> tripletEmiter.emit(richSentence).stream()))
                .collect(toList());

    }
    private List<TripletEmiter> tripletEmiters() {
        return Collections.singletonList(
                new EnglishEmitter()
        );
    }

}
