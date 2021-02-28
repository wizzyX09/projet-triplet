package ht.kreyola.kreyolaparser.service.parsers;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import ht.kreyola.kreyolaparser.contants.Delimiters;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import ht.kreyola.kreyolaparser.model.TripletRequest;
import ht.kreyola.kreyolaparser.service.emitters.TripletEmiter;
import ht.kreyola.kreyolaparser.service.emitters.FrenchEmitter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ht.kreyola.kreyolaparser.contants.ParsersUrl.FRENCH;


@Service
public class FrenchParser implements Parser {

    LexicalizedParser lp = LexicalizedParser.loadModel(FRENCH.value());
    List<TripletEmiter> emitters = tripletEmiters();


    private List<String> toSentences(String useCase) {
        String[] sentences = useCase.split(Delimiters.DOT.value());
        return Arrays.stream(sentences).collect(Collectors.toList());
    }

    @Override
    public List<Triplet> toTriplets(final TripletRequest tripletRequest) {
         List<RichSentence> sentences = tripletRequest.getSentences();

        List<RichSentence> richSentences = sentences.stream().map(sentence -> {
            List<CoreLabel> rawWords = Sentence.toCoreLabelList(sentence.getWords().stream().toArray(String[]::new));
            Tree parse = lp.apply(rawWords);
            sentence.setTaggedWords(parse.taggedYield());
            return sentence;
        }).collect(Collectors.toList());

        return richSentences
                .stream()
                .flatMap(richSentence -> emitters.stream().flatMap(tripletEmiter -> tripletEmiter.emit(richSentence).stream()))
                .collect(Collectors.toList());

    }
    private List<TripletEmiter> tripletEmiters() {
        return Collections.singletonList(
                new FrenchEmitter()
        );
    }

}
