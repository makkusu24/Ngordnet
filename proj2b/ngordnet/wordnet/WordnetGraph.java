package ngordnet.wordnet;

import ngordnet.ngrams.TimeSeries;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordnetGraph {

    private DirectedGraph hyponymsGraph;
    private HashMap<Integer, String> idKeys;
    private HashMap<String, List<Integer>> wordKeys;

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
        this.hyponymsGraph = new DirectedGraph();
        StringTokenizer hyponymsTokenizer = new StringTokenizer(contentHyponyms, "\n");
        StringTokenizer synsetsTokenizer = new StringTokenizer(contentSynsets, "\n");
        while (hyponymsTokenizer.hasMoreTokens()) { // parse hyponyms file
            String token = hyponymsTokenizer.nextToken();
            if (!token.isEmpty()) {
                String[] subToken = token.split(",");
                if (!hyponymsGraph.hasNode(Integer.parseInt(subToken[0]))) {
                    hyponymsGraph.createNode(Integer.parseInt(subToken[0]));
                }
                for (int i = 1; i < subToken.length; i++) {
                    hyponymsGraph.neighbors(Integer.parseInt(subToken[0])).add(Integer.parseInt(subToken[i]));
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
            String[] split = idKeys.get(i).split(" ");
            for (String word : split) {
                if (!wordKeys.containsKey(word)) {
                    wordKeys.put(word, new ArrayList<>());
                }
                wordKeys.get(word).add(i);
            }
        }
    }

    public String idConvert(int synsetID) {
        return idKeys.getOrDefault(synsetID, null);
    }

    public List<Integer> wordConvert(String word) {
        return wordKeys.getOrDefault(word, Collections.emptyList());
    }

    public TreeSet<Integer> findIntHyponyms(List<Integer> synsetID) {
        TreeSet<Integer> hyponyms = new TreeSet<>();
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> bfsQueue = new LinkedList<>();
        for (int i : synsetID) {
            bfsQueue.offer(i);
        }
        while (!bfsQueue.isEmpty()) {
            int current = bfsQueue.poll();
            visited.add(current);
            hyponyms.add(current);
            for (int nextNode : hyponymsGraph.getOrDefault(current, Collections.emptyList())) {
                if (!visited.contains(nextNode)) {
                    bfsQueue.offer(nextNode);
                }
            }
        }
        return hyponyms;
    }

    public String findStrHyponyms(List<Integer> synsetID) {
        TreeSet<Integer> intSet = findIntHyponyms(synsetID);
        TreeSet<String> returnSet = new TreeSet<>();
        for (int id : intSet) {
            if (idConvert(id).contains(" ")) {
                String[] split = idConvert(id).split(" ");
                for (String word : split) {
                    returnSet.add(word);
                }
            } else {
                returnSet.add(idConvert(id));
            }
        }
        return returnSet.toString();
    }

    public String findMultiStrHyponyms(List<String> words) {
        TreeSet<String> result = new TreeSet<>();
        Set<Integer> baseCompare = new TreeSet<>(findIntHyponyms(wordConvert(words.get(0))));
        for (int i = 1; i < words.size(); i++) {
            baseCompare.retainAll(findIntHyponyms(wordConvert(words.get(i))));
        }
        for (int node : baseCompare) {
            if (idConvert(node).contains(" ")) {
                String[] split = idConvert(node).split(" ");
                for (String word : split) {
                    result.add(word);
                }
            } else {
                result.add(idConvert(node));
            }
        }
        return result.toString();
    }

}
