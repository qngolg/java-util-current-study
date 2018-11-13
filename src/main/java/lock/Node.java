package lock;

/**
 * @Author qiangl
 * Created by qgl on 2018/10/24.
 */
public class Node {

    private Node prev;

    private Node next;

    public Node(Node prev,Node next){
        this.prev = prev;
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


    public static void main(String[] args) {

        Node node = new Node(null,null);
        Node node1 = new Node(node,null);

    }
}
