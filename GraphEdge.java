public class GraphEdge {

    //Attributes

    //Attributes referencing from which node and to which node the edge is going
    protected GraphNode source;
    protected GraphNode destination;

    //Attributes referencing the location of the edge on the edge list in the source's list
    protected GraphEdge nextEdge;
    protected GraphEdge prevEdge;

    protected GraphEdge transposedEdge; //Edge in the transposed graph correlated to the current edge

    //Constructors
    public GraphEdge(GraphNode source, GraphNode destination) {
        this.source = source;
        this.destination = destination;
    }

    //Getters & Setters
    public GraphNode getSource() {
        return source;
    }
    public void setSource(GraphNode source) {
        this.source = source;
    }
    public GraphNode getDestination() {
        return destination;
    }
    public void setDestination(GraphNode destination) {
        this.destination = destination;
    }
    public GraphEdge getNextEdge() {
        return nextEdge;
    }
    public void setNextEdge(GraphEdge nextEdge) {
        this.nextEdge = nextEdge;
    }
    public GraphEdge getPrevEdge() {
        return prevEdge;
    }
    public void setPrevEdge(GraphEdge prevEdge) {
        this.prevEdge = prevEdge;
    }
    public GraphEdge getTransposedEdge() {
        return transposedEdge;
    }
    public void setTransposedEdge(GraphEdge transposedEdge) {
        this.transposedEdge = transposedEdge;
    }
}
