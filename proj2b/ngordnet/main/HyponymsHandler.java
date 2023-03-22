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

    public String topKWords(List<String> hyponyms, int start, int end, int k) {
        Set<String> baseCompare = new TreeSet<>(graph.findSetHyponyms(graph.wordConvert(hyponyms.get(0)))); // get IDs of all hyponyms
        TreeMap<Integer, List<String>> sortMap = new TreeMap<>(Collections.reverseOrder());
        if (hyponyms.size() > 1) {
            for (int i = 1; i < hyponyms.size(); i++) {
                baseCompare.retainAll(graph.findSetHyponyms(graph.wordConvert(hyponyms.get(i))));
            }
        }
        for (String word : baseCompare) { // get summed frequencies of the hyponyms
            TimeSeries refTS = map.countHistory(word, start, end);
            int sum = 0;
            for (double value : refTS.values()) {
                sum += value;
            }
            List<String> addSum = sortMap.getOrDefault(sum, new ArrayList<>());
            addSum.add(word);
            sortMap.put(sum, addSum);
        }
        List<String> kWords = new ArrayList<>();
        int countIndex = 0;
        for (Map.Entry<Integer, List<String>> entry : sortMap.entrySet()) {
            List<String> addSum = entry.getValue();
            Collections.sort(addSum);
            int count = entry.getKey();
            for (String word : addSum) {
                kWords.add(word);
                countIndex += 1;
                if (countIndex >= k) {
                    break;
                }
            }
            if (countIndex >= k) {
                break;
            }
        }
        Collections.sort(kWords);
        return kWords.toString();
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
            result = topKWords(words, startYear, endYear, k);
        }
        return result;
    }
}
