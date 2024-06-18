import java.io.DataOutputStream;
import java.io.IOException;

public class RootedTree {

    //Attributes
    TreeNode root; //The root node of the tree

    //Constructors
    public RootedTree() {
    }
    public RootedTree(TreeNode root) {
        this.root = root;
    }

    //Getters & Setters
    public TreeNode getRoot() {
        return root;
    }
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    //Methods

    /**
     * Print to a data output stream the values of the tree in the following format:
     * 1. Tree nodes with the depth of i will be printed on the i+1 row
     * 2. The values of the nodes on each row will be separated by ","
     * 3. The values of the nodes on each row will be printed from left to right
     * Time complexity: O(k = num_of_tree_nodes)
     * @param out A DataOutputStream to print onto
     * @throws IOException
     */
    public void printByLayer(DataOutputStream out) throws IOException {
        //Create queue with the root in it
        MyQueue<TreeNode> queue = new MyQueue<>();
        queue.enqueue(root);

        int previousDepth = -1; //Initialize for root depth

        while (!queue.isEmpty()){ //Run through all nodes on the tree from closest to furthest from the root
            TreeNode printedNode = queue.dequeue(); //Extract tree node to print

            //Add all children of the printed node to the queue
            for (TreeNode child = printedNode.getLeftChild() ; child != null ; child = child.getRightSibling()){
                queue.enqueue(child);
            }

            //Print "," or "\n" or none accordingly
            if (printedNode != root) {
                if (previousDepth != printedNode.getDepth()) {
                    out.writeBytes(System.lineSeparator());
                } else {
                    out.writeBytes(",");
                }
            }

            out.writeBytes("" + printedNode.getStoredKey()); //Print node's value

            previousDepth = printedNode.getDepth(); //Update previous depth
        }
    }

    /**
     * Print the tree in preorder sequence
     * @param out A DataOutputStream to print onto
     * @throws IOException
     */
    public void preorderPrint(DataOutputStream out) throws IOException {
        preorderPrint(out, root, false); //Preorder print starting from root. Don't print "," at the beginning
    }

    /*
    Print to a data output stream the tree rooted at the given node in preorder sequence
    printComma refers to whether to print "," before the current node's value
     */
    private void preorderPrint(DataOutputStream out, TreeNode node, boolean printComma) throws IOException {
        //Print node's value, and "," if needed. "," will be printed before every value apart from the root
        if (printComma)
            out.writeBytes(",");
        out.writeBytes("" + node.getStoredKey());

        if (node.getLeftChild() != null) //Preorder print from the left child if possible
            preorderPrint(out, node.getLeftChild(), true);

        if (node.getRightSibling() != null) //Preorder print from the right sibling if possible
            preorderPrint(out, node.getRightSibling(), true);
    }

}
