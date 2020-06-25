import java.util.LinkedList;

class StringTest {
    public static void main(String[] args) {
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();
        node1.val = 1;
        node2.val = 2;
        node3.val = 3;
        node1.child = node2;
        node2.child = node3;
        StringTest s = new StringTest();
        s.flatten(node1);
        String a = "/*123*/";
        String[] list = a.split("\\/\\*");
        System.out.println(list[0]);
        System.out.println(list[1]);
    }

    public Node flatten(Node head) {
        if(head == null) {
            return head;
        }
        LinkedList<Node> stack = new LinkedList<>();
        Node curNode = new Node();
        curNode.next = head;
        head.prev = curNode;
        stack.addFirst(curNode);
        while(!stack.isEmpty()) {
            if(curNode.child != null) {
                stack.addFirst(curNode);
                curNode = curNode.child;
                continue;
            }
            else {
                if(curNode.next != null) {
                    curNode = curNode.next;
                    continue;
                }
                else {
                    Node temp = stack.removeFirst();
                    curNode.next = temp.next;
                    if(curNode.next != null)
                        curNode.next.prev = curNode;
                    temp.next = temp.child;
                    if(temp.next == null) {
                        break;
                    }
                    temp.next.prev = temp;
                    temp.child = null;
                }
            }
        }
        head.prev = null;
        return head;
    }
}


// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
