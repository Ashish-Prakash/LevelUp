import java.util.Scanner;

public class DLL {
    public static Scanner scn = new Scanner(System.in);

    public static class DoublyLinkedList {
        private class Node {
            int val;
            Node prev;
            Node next;

            Node(int val) {
                this.val = val;
                this.prev = null;
                this.next = null;
            }
        }

        private Node head = null;
        private Node tail = null;
        private int size = 0;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node curr = head;
            sb.append("[");
            while (curr != null) {
                sb.append(curr.val);
                if (curr.next != null) {
                    sb.append(", ");
                }
                curr = curr.next;
            }
            sb.append("]");
            return sb.toString();
        }

        public void displayForw() {
            StringBuilder sb = new StringBuilder();
            Node curr = this.head;
            sb.append("[");
            while (curr != null) {
                sb.append(curr.val);
                if (curr.next != null) {
                    sb.append(", ");
                }
                curr = curr.next;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public void displayBack() {
            StringBuilder sb = new StringBuilder();
            Node curr = this.tail;
            sb.append("[");
            while (curr != null) {
                sb.append(curr.val);
                if (curr.prev != null) {
                    sb.append(", ");
                }
                curr = curr.prev;
            }
            sb.append("]");
            System.out.println(sb.toString());
        }

        public int size() {
            return this.size;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        private boolean ListIsEmptyException() {
            if (this.size == 0) {
                System.out.print("ListIsEmpty: ");
                return true;
            }
            return false;
        }

        private boolean indexIsInvalidException(int index, int leftRange, int rightRange) {
            if (index < leftRange || index > rightRange) {
                System.out.print("IndexIsInValid: ");
                return true;
            }
            return false;
        }

        private void addFirstNode(Node node) {
            if (this.size == 0) {
                this.head = node;
                this.tail = node;
            } else {
                node.next = this.head;
                this.head.prev = node;
                this.head = node;
            }
            this.size++;
        }

        public void addFirst(int val) {
            Node node = new Node(val);
            addFirstNode(node);
        }

        private void addLastNode(Node node) {
            if (this.size == 0) {
                this.head = this.tail = node;
            } else {
                this.tail.next = node;
                node.prev = this.tail;
                this.tail = node;
            }
            this.size--;
        }

        public void addLast(int val) {
            Node node = new Node(val);
            addLastNode(node);
        }

        public void addNodeAt(int idx, Node node) {
            if (idx == 0) {
                addFirstNode(node);
            } else if (idx == this.size) {
                addLastNode(node);
            } else {
                Node forw = getNodeAt(idx);
                Node prev = forw.prev;

                prev.next = node;
                node.prev = prev;

                node.next = forw;
                forw.prev = node;
                this.size++;
            }
        }

        public void addAt(int index, int data) {
            if (indexIsInvalidException(index, 0, this.size)) {
                System.out.println(-1);
                return;
            } else {
                Node node = new Node(data);
                addNodeAt(index, node);
            }
        }

        public void addBefore(Node refNode, int data) {
            Node node = new Node(data);
            Node prev = refNode.prev;
            if (prev == null) {
                refNode.prev = node;
                node.next = refNode;
                this.head = node;
            } else {
                prev.next = node;
                node.prev = prev;
                node.next = refNode;
                refNode.prev = node;
            }
            this.size++;
        }

        public void addBefore(int idx, int data) {
            Node node = getNodeAt(idx);
            addBefore(node, data);
        }

        public void addAfter(Node refNode, int data) {
            Node node = new Node(data);
            Node next = refNode.next;
            if (next == null) {
                refNode.next = node;
                node.prev = refNode;
                this.tail = node;
            } else {
                refNode.next = node;
                node.prev = refNode;
                node.next = next;
                next.prev = node;
            }
            this.size++;
        }

        public void addAfter(int idx, int data) {
            Node node = getNodeAt(idx);
            addAfter(node, data);
        }

        private Node removeFirstNode() {
            Node node = this.head;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node forw = this.head.next;
                forw.prev = null;
                node.next = null;
                this.head = forw;
            }
            this.size--;
            return node;
        }

        public int removeFirst() {
            if (ListIsEmptyException()) {
                return -1;
            }
            return removeFirstNode().val;
        }

        private Node removeLastNode() {
            Node node = this.tail;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else {
                Node slast = this.tail.prev;
                slast.next = null;
                this.tail.prev = null;
                this.tail = slast;
            }
            this.size--;
            return node;
        }

        public int removeLast() {
            if (ListIsEmptyException()) {
                return -1;
            }
            return removeLastNode().val;
        }

        public Node removeAtNode(int idx) {
            if (idx == 0) {
                return removeFirstNode();
            }
            if (idx == this.size - 1) {
                return removeLastNode();
            } else {
                Node node = getNodeAt(idx);
                Node forw = node.next;
                Node prev = node.prev;

                prev.next = forw;
                forw.prev = prev;

                node.next = null;
                node.prev = null;
                this.size--;
                return node;
            }
        }

        public int removeAt(int index) {
            if (ListIsEmptyException()) {
                return -1;
            } else if (indexIsInvalidException(index, 0, this.size - 1)) {
                return -1;
            }
            return removeAtNode(index).val;
        }

        public Node removeAfterNode(Node refNode) {
            Node forw = refNode.next;
            if (forw.next == null) {
                refNode.next = null;
                forw.prev = null;
                this.tail = refNode;
            } else {
                Node next = forw.next;
                refNode.next = next;
                next.prev = refNode;
                forw.prev = null;
                forw.next = null;
            }
            this.size--;
            return forw;
        }

        public int removeAfter(Node refNode) {
            if (refNode.next == null) {
                System.out.print("LocationIsInvalid: ");
                return -1;
            } else {
                return removeAfterNode(refNode).val;
            }
        }

        public int removeAfter(int idx) {
            Node node = getNodeAt(idx);
            return removeAfter(node);
        }

        public Node removeBeforeNode(Node refNode) {
            Node prev = refNode.prev;
            if (prev.prev == null) {
                prev.next = null;
                refNode.prev = null;
                this.head = refNode;
            } else {
                Node pprev = prev.prev;
                pprev.next = refNode;
                refNode.prev = pprev;
                prev.next = null;
                prev.prev = null;
            }
            this.size--;
            return prev;
        }

        public int removeBefore(Node refNode) {
            if (refNode.prev == null) {
                System.out.print("LocationIsInvalid: ");
                return -1;
            } else {
                return removeBeforeNode(refNode).val;
            }
        }

        public int removeBefore(int idx) {
            Node node = getNodeAt(idx);
            return removeBefore(node);
        }

        public int removeNode(Node refNode) {
            Node prev = refNode.prev, next = refNode.next;
            if (this.size == 1) {
                this.head = this.tail = null;
            } else if (prev == null) {
                this.head = next;
            } else if (next == null) {
                this.tail = prev;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            refNode.next = refNode.prev = this.head.prev = this.tail.next = null;
            this.size--;
            return refNode.val;
        }

        public int getFirst() {
            if (ListIsEmptyException()) {
                return -1;
            }
            return this.head.val;
        }

        public int getLast() {
            if (ListIsEmptyException()) {
                return -1;
            }
            return this.tail.val;
        }

        public Node getNodeAt(int idx) {
            Node curr = head;
            while (idx-- > 0) {
                curr = curr.next;
            }
            return curr;
        }

        public int getAt(int index) {
            if (ListIsEmptyException()) {
                return -1;
            } else if (indexIsInvalidException(index, 0, this.size - 1)) {
                return -1;
            } else {
                Node node = getNodeAt(index);
                return node.val;
            }
        }
    }

    public static void main(String[] args) {
        
    }
}
