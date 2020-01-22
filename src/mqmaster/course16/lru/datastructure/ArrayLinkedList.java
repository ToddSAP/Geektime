package mqmaster.course16.lru.datastructure;

import java.util.*;

/**
 *      doubly linked list + array implements LRU data structure
 *      I think it's NOT efficient way to do that, the better approach is Hash + array + singly linked list.
 *  Hash is utilized to target key in array, and singly linked list is utilized to resolve Hash conflict.However,
 *  I don't implement treefiy when Hash conflict, because it's too complex to me, will do it in the future, just
 *  like what HashMap does
 */
public class ArrayLinkedList<K,V> {
    private Node<K,V> head;
    private Node<K,V> tail;
    private Node<K,V>[] table;
    private int size;
    private int capacity = 64;
    // switch to control evict, if false, the data structure behaves like queue.
    private boolean evict = true;

    public ArrayLinkedList (int capacity, boolean evict) {
        this.capacity = capacity;
        this.evict = evict;
        table = new Node[capacity];
    }

    final int hashCode(Object key) {
        int h;
        return key==null?0:((h=key.hashCode()) ^ (h>>>16));
    }

    public V put(K key, V value) {
        int l = table.length, h = hashCode(key), i = (l-1)&h;
        Node<K,V> p, e, n;

        // link list node added
        if (isFull()) {
            removeFirst();
        }
        if (tail!=null) {
            e = new Node(hashCode(key), key, tail, value, null);
            tail.next = e;
            tail = e;
        } else {
           e = new Node(h, key, null, value, null);
           head = e;
           tail = e;
        }

        // searching address
        if (table[i]==null) {
            table[i] = e;
        } else {
            //if hash conflict, find next available slot in array, then link element to the slot
            int j = (i+1)%capacity;
            while (j<capacity) {
                if (table[j]==null) {
                    table[j] = e;
                    break;
                }
                j++;
            }
        }
        size++;
        return value;
    }

    public V get(K key) {
        Node<K,V> p = table[getPositionInArray(key)];
        V result = null;
        if (p != null) {
            //if hash conflict, move p to head and loop doubly linked list until tail to find the right element
            if (p.hash==hashCode(key) && (p.key==key || (key!=null && p.key.equals(key)))) {
                result = p.value;
            } else {
                p = head;
                while (p!=null) {
                    if (p.hash == hashCode(key) && (p.key == key || (key != null && p.key.equals(key)))) {
                        result = p.value;
                        break;
                    }
                    p = p.next;
                }
            }

            if (evict) {
                afterNodeFound(p);
            }
        }
        return result;
    }

    public Object[] keys() {
        ArrayList result = new ArrayList();
        Node<K,V> c=head;
        int i=0;
        while (c!=null) {
            result.add(c.key);
            c = c.next;
        }
        return result.toArray();
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        Node<K,V> c = head;
        while (c!=null) {
            sb.append("{").append(c.key).append(",").append(c.value).append("}\n");
            c = c.next;
        }

        sb.append("]");
        return sb.toString();
    }

    private void removeFirst() {
        if (head!=null) {
            int l = table.length, h = hashCode(head.key), i = (l-1)&h;
            table[i] = null;

            Node<K,V> n = head.next;

            head.next = null;
            head.value = null;
            n.prev = null;
            head = n;
            size--;

        }
    }

    private void afterNodeFound(Node<K,V> node) {
        Node<K,V> p = node.prev, n = node.next, last = tail;
        int l = table.length, h = hashCode(node.key), i = (l-1)&h;
        //the node is not last one, then need to add the node to tail
        if (last!=node) {
            if (p==null) { //means the node is head, then move head to the node's next
                head = n;
            } else {
                p.next = n;
            }
            n.prev = p;

            node.prev = last;
            last.next = node;
            node.next = null;

            tail = node;
        }
    }

    private boolean isFull() {
        return size+1>capacity;
    }

    private int getPositionInArray(K key) {
        int l = table.length, h = hashCode(key);
        return (l-1)&h;
    }





    private class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> prev;
        Node<K,V> next;

        Node (int hash, K key, Node<K,V> prev, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.prev = prev;
            this.value = value;
            this.next = next;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final boolean equals(Node<K,V> node) {
            if (this==node)
                return true;
            if (Objects.equals(this.key, node.key) &&
                Objects.equals(this.value, node.value)) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "{"+key+","+value+"}";
        }
    }
}
