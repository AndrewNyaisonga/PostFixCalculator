/**
 * Created by andrewnyaisonga on 3/2/17.
 */
public class Stacks<AnyType> {

    Node<AnyType> head;

    public class Node<AnyType>{
        public Node next;
        public AnyType data;
    }

    Stacks(){
        head = null;
    }


    public boolean isEmpty(){
        if(head == null){
            return true;
        }
        return false;
    }

    public void push(AnyType x){
        Node temp = new Node();
        temp.data =x;
        if(head==null){
            head= temp;
        }
        else{
            Node current = head;
            head = temp;
            head.next = current;
        }
    }

    public AnyType pop(){
        if(isEmpty()){
            return null;
        }
        AnyType temp = head.data;
        head=head.next;
        return temp;
    }

    public AnyType peek(){
        if(head == null){
            return null;
        }
        return head.data;
    }

    public void printStacks(){
        Node temp = head;
        while(temp!=null){
            System.out.print(temp.data+" ");
            temp = temp.next;
        }
        System.out.println();
    }

}

