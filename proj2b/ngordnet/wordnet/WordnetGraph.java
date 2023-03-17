package ngordnet.wordnet;

import ngordnet.ngrams.TimeSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class WordnetGraph {

    private DirectedGraph hyponymsGraph;
    private HashMap<Integer, String> idKeys;
    private HashMap <String, Integer> wordKeys;

    public WordnetGraph(String synsetFile, String hyponymFile) {
        String contentHyponyms;
        String contentSynsets;
        try {
            contentHyponyms = new String(Files.readAllBytes(Paths.get(hyponymFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            contentSynsets = new String(Files.readAllBytes(Paths.get(synsetFile)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.idKeys = new HashMap<>();
        this.wordKeys = new HashMap<>();
        this.hyponymsGraph =new DirectedGraph();
        StringTokenizer hyponymsTokenizer = new StringTokenizer(contentHyponyms, "\n");
        StringTokenizer synsetsTokenizer = new StringTokenizer(contentSynsets, "\n");
        while (hyponymsTokenizer.hasMoreTokens()) { // parse hyponyms file
            String token = hyponymsTokenizer.nextToken();
            if (!token.isEmpty()) {
                String[] subToken = token.split(",");
                if (hyponymsGraph.hasNode(Integer.parseInt(subToken[0]))) {
                    for (int i = 1; i < subToken.length; i++) {
                        hyponymsGraph.neighbors(Integer.parseInt(subToken[0])).add(Integer.parseInt(subToken[i]));
                    }
                } else {
                    hyponymsGraph.createNode(Integer.parseInt(subToken[0]));
                    for (int i = 1; i < subToken.length; i++) {
                        hyponymsGraph.neighbors(Integer.parseInt(subToken[0])).add(Integer.parseInt(subToken[i]));
                    }
                }
            }
        }
        while (synsetsTokenizer.hasMoreTokens()) { // parse synset file into map (ID, word)
            String token = synsetsTokenizer.nextToken();
            if (!token.isEmpty()) {
                String[] subToken = token.split(",");
                idKeys.put(Integer.parseInt(subToken[0]), (subToken[1]));
            }
        }
        for (int i : idKeys.keySet()) { // create reversed map (word, ID)
            if (idKeys.get(i).contains(" ")) {
                String[] split = idKeys.get(i).split(" ");
                for (String word : split) {
                    wordKeys.put(word, i);
                }
            } else {
                wordKeys.put(idKeys.get(i), i);
            }
        }
    }

    public String IDConvert(int synsetID) {
        return idKeys.getOrDefault(synsetID, null);
    }

    public Integer WordConvert(String word) {
        // take into account that _ denotes multi-word collocations
        return wordKeys.getOrDefault(word, null);
    }

    //TODO: graph traversal --> find hyponyms of synset ID (remove duplicates [add to TreeSet], alphabetize [default comparator])

}
