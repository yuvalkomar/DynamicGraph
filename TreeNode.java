public class TreeNode {

    //Attributes

    //Attributes for left child right sibling rooted tree representation
    protected TreeNode parent;
    protected TreeNode leftChild;
    protected TreeNode rightChild;
    protected TreeNode rightSibling;

    protected int storedKey; //Stored node from the graph in the tree node
    protected int depth = 0; //Length of the path from the root the node

    //Constructors
    public TreeNode(TreeNode parent, int storedKey) {
        //Set given values
        this.parent = parent;
        if (storedKey >= 0)
            this.storedKey = storedKey;

        //If there is a parent given for the node, adjust attributes accordingly
        if (parent != null) {
            depth = parent.depth + 1;

            /*
            Add the new node as the right child of the parent
            If the parent has no children then set the new node as the left child as well
            Otherwise make the previous right child to point to the new node
             */
            if (parent.leftChild != null)
                parent.rightChild.rightSibling = this;
            else
                parent.leftChild = this;

            //Set the new node as the parent's right child
            parent.rightChild = this;
        }
    }

    //Getters & Setters
    public TreeNode getParent() {
        return parent;
    }
    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    public TreeNode getLeftChild() {
        return leftChild;
    }
    public void setLeftChild(TreeNode leftChild) {
        this.leftChild = leftChild;
    }
    public TreeNode getRightChild() {
        return rightChild;
    }
    public void setRightChild(TreeNode rightChild) {
        this.rightChild = rightChild;
    }
    public TreeNode getRightSibling() {
        return rightSibling;
    }
    public void setRightSibling(TreeNode rightSibling) {
        this.rightSibling = rightSibling;
    }
    public int getStoredKey() {
        return storedKey;
    }
    public void setStoredKey(int storedKey) {
        if (storedKey >= 0)
            this.storedKey = storedKey;
    }
    public int getDepth() {
        return depth;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
}