package com.zms.collection;

import com.zms.collection.domain.Node;

/**
 * @Description : TODO
 * @author: zeng.maosen
 * @date: 2023/2/8
 * @version: 1.0
 */
public class HashNode<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final int NODE_LINK_LENGTH_THRESHOLD = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;
    private int threshold;
    private Node<K, V>[] nodes;
    private int nodesLength;
    private Node<K, V> setNode;
    private Node<K,V> setCurNode;


    public HashNode(int initSize) {
        this.threshold = tableSizeFor(initSize);
        this.nodes = (Node<K, V>[]) new Node[threshold];
        this.size = 0;
    }

    public HashNode() {
        this.nodes = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
        this.threshold = DEFAULT_INITIAL_CAPACITY;
        this.size = 0;
    }


    public void put(K key, V value) {
        if (size >= threshold) {
            this.threshold = tableSizeFor(size + 1);
            resize();
        }
        int hashCode = hash(key);
        int index = index(hashCode);
        Node<K, V> node = new Node<>(hashCode, key, index, value, 1);
        Node<K, V> oldNode = nodes[index];
        if (oldNode == null) {
            if(size==0){
                setNode = node;
                setCurNode = node;
            }else {
                setCurNode.setSetIterateNext(node);
                setCurNode = setCurNode.getSetIterateNext();
            }
            nodes[index] = node;
            size++;
            nodesLength++;
            return;
        }
        if (isCompletelyEqual(oldNode, node)) {
            nodes[index].setValue(value);
            return;
        }
        Node<K, V> temp = nodes[index];
        addNode(temp, node);
        size++;
        setCurNode.setSetIterateNext(node);
        setCurNode = setCurNode.getSetIterateNext();
    }

    private boolean isCompletelyEqual(Node<K, V> oldNode, Node<K, V> node) {
        return oldNode.getKey().equals(node.getKey()) && oldNode.getHashCode() == node.getHashCode();
    }

    private boolean isOnlyIndexEqual(Node<K, V> oldNode, Node<K, V> node) {
        return (!oldNode.getKey().equals(node.getKey()))
                && (oldNode.getIndexValue() == oldNode.getIndexValue());
    }

    public V getValue(K key) {
        int hashCode = hash(key);
        int i = index(hashCode);
        if (nodes[i] != null) {
            Node<K, V> temp = nodes[i];
            while (temp != null) {
                if (temp.getKey().equals(key)) {
                    return temp.getValue();
                }
                temp = temp.getNextNode();
            }
        }
        return null;
    }

    public void remove(K key) {
        int hashCode = hash(key);
        int i = index(hashCode);
        if (nodes[i] != null) {
            Node<K, V> node = removeNode(nodes[i], key);
            nodes[i] = node;
        }
    }

    public Node<K, V>[] getSetNode() {
        Node<K, V>[] setNode = (Node<K, V>[]) new Node[size];
        int len = 0;
//        for (Node<K, V> kvNode : nodes) {
//            if (kvNode != null) {
//                Node<K, V> cur = kvNode;
//                while (cur != null) {
//                    int hashCode = hash(cur.getKey());
//                    Node<K, V> node = new Node<>(hashCode, cur.getKey(), cur.getIndexValue(), cur.getValue(), 0);
//                    setNode[len] = node;
//                    len++;
//                    cur = cur.getNextNode();
//                }
//            }
//        }
        Node<K,V> cur = this.setNode;
        for (int i = 0; i < size; i++) {
            setNode[i] = cur;
            cur = cur.getSetIterateNext();
        }
        return setNode;
    }


    private static int hash(Object key) {
        int h;
        //计算key的hashCode, h = Objects.hashCode(key)
        //h >>> 16表示对h无符号右移16位，高位补0，然后h与h >>> 16按位异或
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public int getSize() {
        return size;
    }

    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private void resize() {
        Node<K, V>[] newNodes = (Node<K, V>[]) new Node[threshold];
        for (Node<K, V> kvNode : nodes) {
            if (kvNode != null) {
                kvNode.setIndexLength(1);
                if (kvNode.getNextNode() == null) {
                    int hashCode = hash(kvNode.getKey());
                    int index = index(hashCode);
                    kvNode.setIndexValue(index);
                    if (newNodes[index] == null) {
                        newNodes[index] = kvNode;
                    }
                    if (isOnlyIndexEqual(newNodes[index], kvNode)) {
                        Node<K, V> temp = newNodes[index];
                        addNode(temp, kvNode);
                    }
                } else {
                    Node<K, V> cur = kvNode;
                    Node<K, V> pre;
                    while (cur != null) {
                        int hashCode = hash(kvNode.getKey());
                        int index = index(hashCode);
                        cur.setIndexValue(index);
                        pre = cur;
                        cur = cur.getNextNode();
                        pre.setPreNode(null);
                        pre.setNextNode(null);
                        if (newNodes[index] == null) {
                            newNodes[index] = pre;
                            continue;
                        }
                        Node<K, V> temp = newNodes[index];
                        addNode(temp, pre);
                    }

                }
            }
        }
        nodes = newNodes;
    }

    private int index(int hashCode) {
        return (threshold - 1) & hashCode;
    }

    private void addNode(Node<K, V> head, Node<K, V> node) {
        if (head == null || node == null) {
            return;
        }
        Node<K, V> cur = head;
        while (cur.getNextNode() != null) {
            cur = cur.getNextNode();
        }
        cur.setNextNode(node);
        node.setPreNode(cur);
        head.setIndexLength(head.getIndexLength() + 1);
    }

    private Node<K, V> insertNode(Node<K, V> head, Node<K, V> node) {
        if (head == null || node == null) {
            return head;
        }
        Node<K, V> cur = head;
        while (cur != null) {
            if (cur.hashCode() > node.hashCode()) {
                break;
            }
            cur = cur.getNextNode();
        }
        if (cur == head) {
            node.setNextNode(cur);
            cur.setPreNode(node);
        } else if (cur == null) {
            addNode(head, node);
        } else {
            Node<K, V> next = cur.getNextNode();
            if (next != null) {
                next.setPreNode(node);
            }
            node.setNextNode(next);
            cur.setNextNode(node);
            node.setPreNode(cur);
        }

        return head.getPreNode();
    }

    private Node<K, V> margeNode(Node<K, V> node) {
        Node<K, V> showNode = node;
        Node<K, V> fastNode = node.getNextNode();

        //找寻快慢节点
        while (fastNode != null && fastNode.getNextNode() != null) {
            showNode = showNode.getNextNode();
            fastNode = fastNode.getNextNode();
            if (fastNode.getNextNode() != null) {
                fastNode = fastNode.getNextNode();
            }
        }
        Node<K, V> next = showNode.getNextNode();
        showNode.setNextNode(null);
        return next;
    }

    private Node<K, V> margeSortNode(Node<K, V> node) {
        if (node == null || node.getNextNode() == null) {
            return node;
        }
        Node<K, V> head1 = node;
        Node<K, V> head2 = margeNode(node);
        head1 = margeSortNode(head1);
        head2 = margeSortNode(head2);
        return margeSort(head1, head2);
    }

    private Node<K, V> margeSort(Node<K, V> node1, Node<K, V> node2) {
        Node<K, V> node = new Node<>();
        Node<K, V> cur1 = node1;
        Node<K, V> cur2 = node2;
        Node<K, V> result = node;
        while (cur1 != null && cur2 != null) {
            if (cur1.hashCode() > cur2.hashCode()) {
                result.setNextNode(cur2);
                cur2.setPreNode(result);
                cur2 = cur2.getNextNode();
            } else {
                result.setNextNode(cur1);
                cur1.setPreNode(result);
                cur1 = cur1.getNextNode();
            }
            result = result.getNextNode();
        }
        if (cur1 != null) {
            result.setNextNode(cur1);
        }
        if (cur2 != null) {
            result.setNextNode(cur2);
        }
        return node.getNextNode();
    }

    private Node<K, V> findNode(Node<K, V> node, int hashCode) {
        if (node == null) {
            return null;
        }
        Node<K, V> cur = node;
        while (cur != null) {
            if (cur.hashCode() == hashCode) {
                break;
            }
            cur = cur.getNextNode();
        }
        return cur;
    }

    private Node<K, V> removeNode(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
        if (node.getKey().equals(key) && node.getHashCode() == hash(key)) {
            size--;
            if(node.getNextNode()!=null){
                node.getNextNode().setIndexLength(node.getIndexLength()-1);
            }
            removeSetNode(key);
            node = node.getNextNode();
            return node;
        }
        Node<K, V> cur = node;
        while (true) {
            if (cur == null) {
                return node;
            }
            if (cur.getKey().equals(key) && cur.getHashCode() == hash(key)) {
                Node<K, V> next = cur.getNextNode();
                Node<K, V> pre = cur.getPreNode();
                if (next != null) {
                    next.setPreNode(pre);
                }
                if (pre != null) {
                    pre.setNextNode(next);
                }
                size--;
                node.setIndexLength(node.getIndexLength()-1);
                removeSetNode(key);
                return node;
            }
            cur = cur.getNextNode();
        }
    }

    private void removeSetNode(K key){
        Node<K, V> cur = this.setNode;
        if (this.setNode.getKey().equals(key) && this.setNode.getHashCode() == hash(key)) {
            Node<K, V> kvNode = setNode.getSetIterateNext();
            setNode = null;
            setNode = kvNode;
            return;
        }
        while (cur.getSetIterateNext() != null) {
            Node<K, V> next = cur.getSetIterateNext();
            if (next.getKey().equals(key) && next.getHashCode() == hash(key)) {
                cur.setSetIterateNext(next.getSetIterateNext());
                break;
            }
            cur = cur.getSetIterateNext();
        }
        if (cur.getSetIterateNext() == null) {
            this.setCurNode = cur;
        }

    }
}
