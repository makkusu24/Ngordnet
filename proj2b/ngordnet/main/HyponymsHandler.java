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
        if (words.size() == 1) {
            String result = graph.findStrHyponyms(graph.wordConvert(words.get(0)));
            return result;
        } else {
            String result = graph.findMultiStrHyponyms(words);
            return result;
        }
    }
}
