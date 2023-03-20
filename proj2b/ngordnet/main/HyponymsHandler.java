package ngordnet.main;

import edu.princeton.cs.algs4.MaxPQ;
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

    public List<String> topKWords(List<Integer> hyponyms, int start, int end, int k) { //handles 1 word for now b/c of return of findSetHyponyms
        //HashMap<String, Integer> mapCounts = sumCountHistory(graph.findSetHyponyms(hyponyms), start, end); // breakpoint
        Comparator<HashMap<String, Integer>> cmptr = new MapComparator();
        //MaxPQ<HashMap<String, Integer>> topK = new MaxPQ<>(mapCounts.size(), cmptr);
        MaxPQ<HashMap<String, Integer>> topK = new MaxPQ<>(cmptr);
        for (String word : graph.findSetHyponyms(hyponyms)) {
            HashMap<String, Integer> newSet = new HashMap<>();
            TimeSeries refTS = map.countHistory(word, start, end);
            int sum = 0;
            for (double value : refTS.values()) {
                sum += value;
            }
            newSet.put(word, sum);
            topK.insert(newSet);
        }
        while (topK.size() > k) {
            topK.delMax();
        }
        ArrayList<String> returnList = new ArrayList<>();
        while (topK.size() > 0) {
            returnList.add(topK.delMax().keySet().toString());
        }
        return returnList;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        String result;
        if (words.size() == 1 && k == 0) {
            result = graph.findStrHyponyms(graph.wordConvert(words.get(0)));
        } else if (words.size() != 1 && k == 0){
            result = graph.findMultiStrHyponyms(words);
        } else {
            result = topKWords(graph.wordConvert(words.get(0)), startYear, endYear, k).toString();
        }
        return result;
    }
}
