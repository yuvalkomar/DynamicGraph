public class GraphNode {

    //Attributes

    //Attributes referencing the location of the node on the node list in the graph
    protected GraphNode nextNode;
    protected GraphNode prevNode;

    protected int key; //The value stored in the node
    protected int inDegree = 0; //Number of edges coming in to the node
    protected GraphEdge headOutEdge; //Head of the list of edges coming out of the node
    protected GraphNode transposedNode; //Node in the transposed graph correlated to the current node
    protected String color; //Color of node. Used in BFS\DFS algorithms

    //Constructors
    public GraphNode(int key) {
        if (key >= 0)
            this.key = key;
    }

    //Getters & Setters
    public GraphNode getNextNode() {
        return nextNode;
    }
    public void setNextNode(GraphNode nextNode) {
        this.nextNode = nextNode;
    }
    public GraphNode getPrevNode() {
        return prevNode;
    }
    public void setPrevNode(GraphNode prevNode) {
        this.prevNode = prevNode;
    }
    public void setKey(int key) {
        if (key >= 0)
            this.key = key;
    }
    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }
    public GraphEdge getHeadOutEdge() {
        return headOutEdge;
    }
    public void setHeadOutEdge(GraphEdge headOutEdge) {
        this.headOutEdge = headOutEdge;
    }
    public GraphNode getTransposedNode() {
        return transposedNode;
    }
    public void setTransposedNode(GraphNode transposedNode) {
        this.transposedNode = transposedNode;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }


    //Methods

    /**
     * Retrieve the key of the node
     * Time complexity: O(1)
     * @return Key value of graph node
     */
    public int getKey() {
        return key;
    }

    /**
     * Retrieve the in-degree of the node
     * Time complexity: O(1)
     * @return In-degree of graph node
     */
    public int getInDegree() {
        return inDegree;
    }

    /**
     * Retrieve the out-degree of the node
     * Time complexity: O(deg_out(node))
     * @return Out-degree of graph node
     */
    public int getOutDegree() {
        int outDegree = 0;
        //Run through all edges going out, and count them
        for (GraphEdge edge = headOutEdge ; edge != null ; edge = edge.getNextEdge())
            outDegree++;
        return outDegree;
    }
}