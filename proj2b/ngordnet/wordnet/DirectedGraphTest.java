package ngordnet.wordnet;

import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import java.util.List;

public class DirectedGraphTest {

    @Test
    public void graphBasicsTest() {
        DirectedGraph a = new DirectedGraph(5);
        a.addEdge(0, 1);
        a.addEdge(1, 2);
        a.addEdge(2, 3);
        a.addEdge(3, 4);

        List<Integer> neighbors = a.neighbors(0);
        boolean nodeTest = a.hasNode(2);
        boolean edgeTest = a.hasEdge(3, 4);

        assertThat(neighbors).isEqualTo(List.of(1));
        assertThat(nodeTest).isEqualTo(true);
        assertThat(edgeTest).isEqualTo(true);
    }
}
