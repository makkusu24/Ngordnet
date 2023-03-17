package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.wordnet.WordnetGraph;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordnetGraph graph;

    public HyponymsHandler(WordnetGraph wng) {
        this.graph = wng;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        List<Integer> wordsID = graph.wordConvert(words.get(0)); // wordsID null if no word exists
        String result = graph.findStrHyponyms(wordsID);
        return result;
    }
}
