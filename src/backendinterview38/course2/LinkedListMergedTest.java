package backendinterview38.course2;

import java.util.HashMap;

/**
 * The Question:
 *     有两个单向链表，这两个单向链表有可能在某个元素合并，
 * 如下图所示的这样，也可能不合并。现在给定两个链表的头指针，
 * 如何快速地判断这两个链表是否合并？如果合并，找到合并的元素，
 * 也就是图中的 x 元素。
 * p1->a->b->x->y->z
 * p2->d->e->f->x->y->z
 *
 * The Thought:
 *      1.traverse two linked list in one while.
 *      2.check and put node address into HashMap.
 *      3.if any node address found in HashMap, means merged.
 *      4.if any linked list traverse ended and
 *         no same node address found in HashMap, not merged.
 */
public class LinkedListMergedTest {
    public static void main(String[] args) {
        // construct merged nodes
        Node<String> p_merge = new Node<>("x");
        p_merge.next = new Node<>("y");
        p_merge.next.next = new Node<>("z");
        p_merge.next.next.next = null;

        // construct p1 nodes
        Node<String> p1 = new Node<>();
        p1.next = new Node<>("a");
        p1.next.next = new Node<>("b");
        p1.next.next.next = p_merge;

        // construct p2 nodes
        Node<String> p2 = new Node<>();
        p2.next = new Node<>("d");
        p2.next.next = new Node<>("e");
        p2.next.next.next = new Node<>("f");
        p2.next.next.next.next = p_merge;

        if (LinkedListMergedTest.checkIfMerged(p1, p2)) {
            System.out.println("Merged");
        } else {
            System.out.println("Not Merged");
        }
    }

    public static <T> boolean checkIfMerged(Node<T> p1, Node<T> p2) {
        HashMap<Node<T>, Node<T>> hashMap = new HashMap<>();
        if (p1 == null && p2 == null) {
            return true;
        }
        while (p1!=null && p2!=null) {
            if (hashMap.get(p1)!=null) {
                return true;
            } else {
                hashMap.put(p1, p1);
                p1 = p1.next;
            }

            if (hashMap.get(p2)!=null) {
                return true;
            } else {
                hashMap.put(p2, p2);
                p2 = p2.next;
            }
        }
        return false;
    }

    static class Node<T> {
        T data;
        Node<T> next;

        Node() {

        }

        Node(T data) {
            this.data = data;
        }
    }
}
