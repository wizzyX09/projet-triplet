package ht.kreyola.kreyolaparser.service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import ht.kreyola.kreyolaparser.model.Delimiters;
import ht.kreyola.kreyolaparser.model.RichSentence;
import ht.kreyola.kreyolaparser.model.Triplet;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ht.kreyola.kreyolaparser.model.ParsersUrl.FRENCH;


@Service
public class FrenchParser implements Parser {

    LexicalizedParser lp = LexicalizedParser.loadModel(FRENCH.value());

    List<TripletEmiter> emiters = getEmiters();

    private List<String> toSentences(String useCase) {
        String[] sentences = useCase.split(Delimiters.DOT.value());
        return Arrays.stream(sentences).collect(Collectors.toList());
    }

    @Override
    public List<Triplet> toTriplets(List<RichSentence> sentences) {
//        List<String> sentences = toSentences(useCase.trim());

        List<RichSentence> richSentences = sentences.stream().map(sentence -> {
            List<CoreLabel> rawWords = Sentence.toCoreLabelList(sentence.getWords().stream().toArray(String[]::new));
            Tree parse = lp.apply(rawWords);
            sentence.setTaggedWords(parse.taggedYield());
            return sentence;
        }).collect(Collectors.toList());

        return richSentences
                .stream()
                .flatMap(richSentence -> emiters.stream().flatMap(tripletEmiter -> tripletEmiter.emit(richSentence).stream()))
                .collect(Collectors.toList());

    }

    private List<TripletEmiter> getEmiters() {
        return Arrays.asList(
                new BaseEmiter()
        );
    }

}
