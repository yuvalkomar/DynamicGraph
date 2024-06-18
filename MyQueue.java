public class MyQueue<T> {

    //Attributes
    protected MyQueueNode<T> head; //The head of the queue. The value to get when getting data from the queue
    protected MyQueueNode<T> tail; //The tail of the queue. The last node to be inserted to the queue

    //Constructors
    public MyQueue() {
    }

    //Getters & Setters
    public MyQueueNode<T> getHead() {
        return head;
    }
    public void setHead(MyQueueNode<T> head) {
        this.head = head;
    }
    public MyQueueNode<T> getTail() {
        return tail;
    }
    public void setTail(MyQueueNode<T> tail) {
        this.tail = tail;
    }

    //Methods

    //Enqueue a value to the end of the queue
    public void enqueue (T addedValue){
        MyQueueNode<T> addedNode = new MyQueueNode<>(addedValue); //Create a new node with the given value
        if (head == null){
            head = addedNode; //If the queue is empty set the head to be the new node
        }
        else {
            tail.setNext(addedNode); //If the queue isn't empty, set the last node in the queue to point to the new node
        }
        tail = addedNode; //Put the new node at the end of the queue
    }

    //Get the value from the head of the queue, and remove it from the queue
    public T dequeue(){
        MyQueueNode<T> removedNode = head; //Retrieve node
        head = head.getNext(); //Adjust the head of the queue
        if (head == null)
            tail = null; //If the queue is empty after we took out the node, clear the tail as well
        return removedNode.getValue(); //Return the value
    }

    //get the value from the head of the queue, without extracting it from the queue
    public T peek(){
        return head.value;
    }

    //Return whether the queue has elements in it
    public boolean isEmpty(){
        return head==null;
    }

    //Reverse the given queue so that the end would be the front and vice-versa
    public MyQueue<T> reverse(){
        MyQueueNode<T> previous = null;
        MyQueueNode<T> current = head;
        MyQueueNode<T> next = null;

        /* For each node in the queue, get it to point to the node previous to it,
         * therefore switching the direction of the queue */
        while (current != null){
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }

        //Swap the head and the tail of the queue
        MyQueueNode<T> temp = head;
        head = tail;
        tail = temp;

        return this;
    }
}
