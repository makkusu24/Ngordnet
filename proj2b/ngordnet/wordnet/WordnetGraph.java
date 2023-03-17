package ngordnet.wordnet;

import java.util.HashMap;
import java.util.List;

public class WordnetGraph {
    //TODO: implement method for file --> graph
    //TODO: graph traversal --> find hyponyms of synset ID (remove duplicates [add to set], alphabetize [sort set])
    //TODO: convert from synset ID to word
    private DirectedGraph hyponymsGraph;
    private HashMap<Integer, List<String>> idKeys;
    private HashMap <String, Integer> wordKeys;

    public WordnetGraph(String synsetFile, String hyponymFile) {

    }

    public List<String> IDConvert(int synsetID) {
        return null;
    }

    public Integer WordConvert(String word) {
        return 0;
    }

}
