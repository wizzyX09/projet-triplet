package ht.kreyola.kreyolaparser.model;

import edu.stanford.nlp.ling.TaggedWord;
import ht.kreyola.kreyolaparser.contants.SentenceStatus;

import java.util.List;

public class RichSentence {

    private String content;
    private SentenceStatus status;
    private List<String> words;
    private List<TaggedWord> taggedWords;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SentenceStatus getStatus() {
        return status;
    }

    public void setStatus(SentenceStatus status) {
        this.status = status;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<TaggedWord> getTaggedWords() {
        return taggedWords;
    }

    public void setTaggedWords(List<TaggedWord> taggedWords) {
        this.taggedWords = taggedWords;
    }
}
