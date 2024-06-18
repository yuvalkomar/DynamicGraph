public class DynamicGraph {

    //Attributes
    protected GraphNode headNode; //Reference to the first node in the graph's nodes list
    protected DynamicGraph transposedGraph; //The transposed graph for the current one

    //Constructors

    /**
     * Build a new empty graph
     * Time complexity: O(1)
     */
    public DynamicGraph() {
        transposedGraph = new DynamicGraph(false);
    }

    /*
    Build a new empty graph with a given transposed graph
    O(1)
     */
    public DynamicGraph(boolean withTransposed){
        if (withTransposed)
            this.transposedGraph = new DynamicGraph();
    }

    //Getters & Setters
    public GraphNode getHeadNode() {
        return headNode;
    }
    public void setHeadNode(GraphNode headNode) {
        this.headNode = headNode;
    }
    public DynamicGraph getTransposedGraph() {
        return transposedGraph;
    }
    public void setTransposedGraph(DynamicGraph transposedGraph) {
        this.transposedGraph = transposedGraph;
    }

    //Methods

    /**
     * Add a new node to the graph with a distinctive key
     * Time complexity: O(1)
     * @param nodeKey The distinctive key of the new node
     * @return The graph node created and inserted into the graph
     */
    public GraphNode insertNode(int nodeKey) {
        GraphNode newNode = new GraphNode(nodeKey); //Create a new node using the given key
        newNode.setNextNode(headNode); //Insert the new node to the start of the list

        //If we can, set the last head's previous node to be the new one
        if (headNode != null)
            headNode.setPrevNode(newNode);

        headNode = newNode; //Set the start of the list to be the new edge

        //If it exists, add a new node to the transposed graph as well
        if (transposedGraph != null)
            newNode.setTransposedNode(transposedGraph.insertNode(nodeKey));

        return newNode; //Return the new node
    }

    /**
     * Remove a node from the graph
     * Time complexity: O(1)
     * @param node The graph node to remove from the graph
     */
    public void deleteNode(GraphNode node) {
        if (node.getInDegree() == 0 && node.getHeadOutEdge() == null) { // Don't delete nodes with edges

            if (node == headNode) { //The deleted node is the head, therefore there is no previous to that node
                headNode = headNode.getNextNode(); //Move head 1 step up

                //If we can, Set the new head's previous node to null
                if (headNode != null)
                    headNode.setPrevNode(null);
            } else { //There is a node before the deleted node
                //Set the node before the one we want to delete to point to the one after
                node.getPrevNode().setNextNode(node.getNextNode());
                //If we can, Set the node after the one we want to delete to point to the one before:
                if (node.getNextNode() != null)
                    node.getNextNode().setPrevNode(node.getPrevNode());
            }

            //Detach the node from the graph
            node.setPrevNode(null);
            node.setNextNode(null);

            //If it exists, remove the node from the transposed graph as well
            if (transposedGraph != null)
                transposedGraph.deleteNode(node.getTransposedNode());
        }
    }

    /**
     * Add a new edge to the graph from a given source to a given destination
     * Time complexity: O(1)
     * @param src The source of the new edge
     * @param dst The destination of the new edge
     * @return The graph edge created and inserted into the graph
     */
    public GraphEdge insertEdge(GraphNode src, GraphNode dst) {
        GraphEdge newEdge = new GraphEdge(src, dst); //Create a new edge using the information given

        //Change source accordingly:
        newEdge.setNextEdge(src.getHeadOutEdge()); //Place the new edge before the head in the edge list in the source
        //If we can, set the new edge to be the previous of the last head
        if (src.getHeadOutEdge() != null)
            src.getHeadOutEdge().setPrevEdge(newEdge);
        src.setHeadOutEdge(newEdge); //Update the new head

        //Change destination accordingly:
        dst.setInDegree(dst.getInDegree() + 1); //Add 1 to the in degree of the destination

        //If it exists, add a new edge to the transposed graph as well, with the reverse direction
        if (transposedGraph!=null)
            newEdge.setTransposedEdge(transposedGraph.insertEdge(dst.getTransposedNode(), src.getTransposedNode()));

        return newEdge;
    }

    /**
     * Remove an edge from the graph
     * Time complexity: O(1)
     * @param edge The graph edge to remove from the graph
     */
    public void deleteEdge(GraphEdge edge) {
        //get the source and destination
        GraphNode src = edge.getSource();
        GraphNode dst = edge.getDestination();

        //Change source accordingly:

        //When we want to delete the first edge in the list
        if (edge == src.getHeadOutEdge()) {
            src.setHeadOutEdge(src.getHeadOutEdge().getNextEdge()); //Move the head 1 move up the list
            //If we can, Set the new head to point back to the one before
            if (edge.getNextEdge() != null)
                src.getHeadOutEdge().setPrevEdge(null);
        }

        //When the deleted edge is not the head, then there is an edge before it
        else {
            //Set the edge before the one we want to delete to point to the one after
            edge.getPrevEdge().setNextEdge(edge.getNextEdge());
            //If we can, Set the edge after the one we want to delete to point back to the one before
            if (edge.getNextEdge() != null)
                edge.getNextEdge().setPrevEdge(edge.getPrevEdge());
        }

        //Detach edge from Graph
        edge.setPrevEdge(null);
        edge.setNextEdge(null);

        //Change Destination accordingly:
        dst.setInDegree(dst.getInDegree() - 1); //Decrease 1 from the in degree of the destination

        //If it exists, remove the edge from the transposed graph as well
        if (transposedGraph!=null)
            transposedGraph.deleteEdge(edge.getTransposedEdge());
    }

    /**
     * Calculate strongly connected components in the graph
     * Time complexity: O(n+m) = Time complexity of DFS
     * @return A rooted tree with a root key of 0, and each child of the root is a strongly connected component
     */
    public RootedTree scc() {
        //create sequence of vertices in order of decreasing v.f
        MyQueue<GraphNode> sequence = this.createSequence();

        //construct transposed graph
        DynamicGraph transposed = getTransposedGraph();

        //Build the rooted tree (and return it), using the sequence and the transposed graph
        return this.getSCCTree(transposed, sequence);
    }

    /*
    Create sequence of vertices in order of decreasing v.f
    A variation of DFS
     */
    private MyQueue<GraphNode> createSequence() {
        MyQueue<GraphNode> sequence = new MyQueue<>(); //Create the queue

        //Whiten the color of all nodes
        for (GraphNode node = headNode; node != null; node = node.getNextNode())
            node.setColor("white");

        //For each undiscovered node, discover it using createSequenceVisit (a variation of DFSVisit)
        for (GraphNode node = headNode; node != null; node = node.getNextNode()) {
            if (node.getColor().equals("white"))
                createSequenceVisit(node, sequence);
        }

        //Clear the color of all transposed nodes
        for (GraphNode node = headNode; node != null; node = node.getNextNode())
            node.setColor(null);

        return sequence.reverse(); //Reverse the sequence, since it's an ascending right now, and return it
    }

    /*
    Add to a given sequence of vertices in order of ascending v.f according to a given node
    A variation of DFS
     */
    private void createSequenceVisit(GraphNode node, MyQueue<GraphNode> sequence) {
        //Color the node gray to indicate it is getting cleared by the DFS
        node.setColor("gray");

        //Scan for neighbors of the current node, and if they are undiscovered yet, visit them
        for (GraphEdge edge = node.getHeadOutEdge(); edge != null; edge = edge.getNextEdge()) {
            GraphNode neighbor = edge.getDestination();
            if (neighbor.getColor().equals("white"))
                createSequenceVisit(neighbor, sequence);
        }

        //Color the node gray to indicate it is cleared by the DFS
        node.setColor("black");

        //Add the node to the sequence in ascending order of v.f
        sequence.enqueue(node);
    }

    /*
    Build a scc tree given the correct sequence and the transposed graph
    A variation of DFS
     */
    private RootedTree getSCCTree(DynamicGraph transposed, MyQueue<GraphNode> sequence) {
        //Create the resulted tree, and it's root
        TreeNode root = new TreeNode(null, 0);
        RootedTree result = new RootedTree(root);

        //Whiten the color of all transposed nodes
        for (GraphNode node = transposed.headNode; node != null; node = node.getNextNode())
            node.setColor("white");

        //Empty the sequence, and for each undiscovered node build a scc in the tree
        while (!sequence.isEmpty()) {
            GraphNode originalNode = sequence.dequeue();
            GraphNode transposedNode = originalNode.getTransposedNode();
            if (transposedNode.getColor().equals("white"))
                getSCCTreeVisit(transposedNode, root);
        }

        //Clear the color of all transposed nodes
        for (GraphNode node = transposed.headNode; node != null; node = node.getNextNode())
            node.setColor(null);

        return result;
    }

    /*
    Add a node to the tree given its parent, and build the tree rooted at the node
    A variation of DFSVisit
     */
    private void getSCCTreeVisit(GraphNode transposedNode, TreeNode parent) {
        /*
        Add the node to the tree in the appropriate place,
        and color it gray to indicate it is getting cleared by the DFS
         */
        transposedNode.setColor("gray");
        TreeNode treeNode = new TreeNode(parent, transposedNode.getKey());

        /*
        Scan for neighbors of the current node
        if they are undiscovered yet, add them to the scc tree under the current one
         */
        for (GraphEdge edge = transposedNode.getHeadOutEdge(); edge != null; edge = edge.getNextEdge()) {
            GraphNode neighbor = edge.getDestination();
            if (neighbor.getColor().equals("white"))
                getSCCTreeVisit(neighbor, treeNode);
        }

        //Color the current node black, to indicate it is fully cleared by the DFS
        transposedNode.setColor("black");
    }


    /**
     * Get the shortest path tree from a given source node on the graph
     * Time complexity: O(n+m) = Time complexity of BFS
     * @param source The shortest path tree's root, from which to calculate the shortest path's from
     * @return The shortest path tree from the source graph node
     */
    public RootedTree bfs(GraphNode source) {
        //Create the resulted tree
        RootedTree result = new RootedTree(new TreeNode(null, source.getKey()));

        //Initialize
        /*
        Whiten the color of all nodes, to indicate they are undiscovered by the BFS
        color the source with gray, to indicate it is discovered by the BFS
         */
        for (GraphNode node = headNode; node != null; node = node.getNextNode())
            node.setColor("white");
        source.setColor("gray");
        /*
        Initialize 2 queues:
        One for regular BFS algorithm
        The second for the corresponding tree node of each node in the regular queue
         */
        MyQueue<GraphNode> queue = new MyQueue<>();
        MyQueue<TreeNode> treeQueue = new MyQueue<>();
        queue.enqueue(source);
        treeQueue.enqueue(result.getRoot());

        /*
        Run BFS algorithm
        Scan through all nodes connected to the given node in the graph
         */
        while (!queue.isEmpty()) {
            GraphNode u = queue.dequeue();
            TreeNode treeNode = treeQueue.dequeue();

            /*
            Run through all neighbors of the current node
            If they are undiscovered:
            Color them gray to indicate they have been discovered by the BFS
            Add them to the regular queue
            Create a new tree node under the current one in the resulted tree
            Add the new tree node to the second queue to correspond with the node in the regular queue
             */
            for (GraphEdge edge = u.getHeadOutEdge(); edge != null; edge = edge.getNextEdge()) {
                GraphNode neighbor = edge.getDestination();
                if (neighbor.getColor().equals("white")) {
                    neighbor.setColor("gray");
                    queue.enqueue(neighbor);
                    treeQueue.enqueue(new TreeNode(treeNode, neighbor.getKey()));
                }
            }
            //Color the node black, to indicate it is fully cleared by the BFS
            u.setColor("black");
        }

        //Clear the color of all nodes
        for (GraphNode node = headNode; node != null; node = node.getNextNode())
            node.setColor("white");

        return result;
    }
}