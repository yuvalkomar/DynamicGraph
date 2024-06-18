public class MyQueueNode <T>{

    //Attributes
    protected T value; //Value stored in the node
    protected MyQueueNode<T> next; //Next node on the queue

    //Constructors
    public MyQueueNode(T value) {
        this.value = value;
    }

    //Getters & Setters
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
    public MyQueueNode<T> getNext() {
        return next;
    }
    public void setNext(MyQueueNode<T> next) {
        this.next = next;
    }
}
