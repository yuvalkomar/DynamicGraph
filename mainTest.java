import java.io.DataOutputStream;
import java.io.IOException;

import static java.lang.System.out;

public class mainTest {
    public static void main(String[] args) throws IOException {
        DataOutputStream s = new DataOutputStream(out);

        DynamicGraph graph = new DynamicGraph();


        GraphNode node2 = graph.insertNode(2);
        GraphNode node3 = graph.insertNode(3);
        GraphNode node1 = graph.insertNode(1);

        GraphEdge edge1 = graph.insertEdge(node1,node2);
        GraphEdge edge2 = graph.insertEdge(node2,node3);
        GraphEdge edge3 = graph.insertEdge(node3,node2);

        System.out.println("");
        graph.scc().printByLayer(s);
        s.writeBytes(System.lineSeparator());
    }
}
