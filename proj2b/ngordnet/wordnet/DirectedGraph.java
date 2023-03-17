package ngordnet.wordnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectedGraph extends HashMap<Integer, List<Integer>> {

    public DirectedGraph() {
        super();
    }

    public DirectedGraph(int v) {
        super();
        for (int i = 0; i < v; i++) {
            this.put(i, new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        DirectedGraph a = new DirectedGraph(5);
        a.addEdge(0, 1);
        a.addEdge(1, 2);
        a.addEdge(2, 3);
        a.addEdge(3, 4);
        System.out.println(a.neighbors(0));
        System.out.println(a.hasNode(1));
        System.out.println(a.hasEdge(3, 4));
    }

    public void createNode(int v) {
        this.put(v, new ArrayList<>());
    }

    public List<Integer> neighbors(int v) {
        return this.get(v);
    }

    public boolean hasNode(int v) {
        return this.containsKey(v);
    }

    public boolean hasEdge(int u, int v) {
        if (!this.containsKey(u)) {
            return false;
        }
        return this.get(u).contains(v);
    }

    public void addEdge(int u, int v) {
        if (!this.containsKey(u)) {
            createNode(u);
        }
        if (!this.containsKey(v)) {
            createNode(v);
        }
        this.get(u).add(v);
    }

}
