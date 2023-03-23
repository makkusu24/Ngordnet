package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.wordnet.WordnetGraph;
import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordnetGraph graph;
    NGramMap map;

    public HyponymsHandler(WordnetGraph wng, NGramMap ngram) {
        this.graph = wng;
        this.map = ngram;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        String result;
        if (k == 0) {
            result = graph.findMultiStrHyponyms(words);
        } else {
            result = graph.topKWords(words, startYear, endYear, k, map);
        }
        return result;
    }
}
