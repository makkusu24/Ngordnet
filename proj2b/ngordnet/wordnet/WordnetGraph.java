package ngordnet.wordnet;

import ngordnet.ngrams.TimeSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class WordnetGraph {
    //TODO: implement method for file --> graph
    //TODO: graph traversal --> find hyponyms of synset ID (remove duplicates [add to set], alphabetize [sort set])
    private DirectedGraph hyponymsGraph;
    private HashMap<Integer, List<String>> idKeys;
    private HashMap <String, Integer> wordKeys;

    public WordnetGraph(String synsetFile, String hyponymFile) {
        String contentHyponyms = null;
        String contentSynsets = null;
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
                String[] subToken = token.split("[\t ]+");
                if (hyponymsGraph.containsKey(subToken[0])) {
                    hyponymsGraph.get(subToken[0]).add(Integer.parseInt(subToken[1]), Integer.parseInt(subToken[2]));
                } else {
                    TimeSeries tempEntry = new TimeSeries();
                    tempEntry.put(Integer.parseInt(subToken[1]), Double.parseDouble(subToken[2]));
                    hyponymsGraph.put(subToken[0], tempEntry);
                }
            }
        }
        while (synsetsTokenizer.hasMoreTokens()) { // parse counts file
            String token = synsetsTokenizer.nextToken();
            if (!token.isEmpty()) {
                String[] subToken = token.split(",");
                idKeys.put(Integer.parseInt(subToken[0]), Integer.parseInt(subToken[1]));
            }
        }
    }

    public List<String> IDConvert(int synsetID) {
        return null;
    }

    public Integer WordConvert(String word) {
        return 0;
    }

}
