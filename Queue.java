/**
 * Created by andrewnyaisonga on 3/2/17.
 */
public class Queue <AnyType> {

    Node<AnyType> head;
    Node<AnyType> tail;

    public class Node<AnyType> {
        public AnyType data;
        public Node next;
        public Node prev;
    }

    Queue() {
        head = null;
        tail = null;
    }

    public void enqueue(AnyType x) {
        Node temp = new Node();
        temp.data = x;
        Node oldlast = tail;
        tail = temp;
        tail.next = null;
        if (isEmpty()) {
            head = tail;
        }
        else {
            oldlast.next = tail;
        }

    }

    public boolean isEmpty(){
        if (head == null) {
            return true;
        }
        return false;
    }

    public AnyType dequeue(){
        if (isEmpty()){
            return null;
        }
        AnyType item = head.data;
        head = head.next;
        return item;
    }

    public AnyType peek() {
        if (head == null)
            return null;
        return head.data;
    }

    public void printQueue(){
        if(head== null){
            System.out.println("This Queue is Empty");
        }
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp = temp.next;
        }
        System.out.println();
    }


}
