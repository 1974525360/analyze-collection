package com.zms.collection.domain;

/**
 * @Description : TODO
 * @author: zeng.maosen
 * @date: 2023/2/8
 * @version: 1.0
 */
public class Node<K,V>{
    private Node<K,V> preNode;
    private Node<K,V> nextNode;
    private Node<K,V> setIterateNext;
    private int hashCode;
    private V value;
    /**
     * 节点数组坐标
     */
    private int indexValue;
    private K key;
    /**
     * 节点深度,以头节点为准
     */
    private int indexLength;

    public Node() {

    }

    public Node<K, V> getPreNode() {
        return preNode;
    }

    public void setPreNode(Node<K, V> preNode) {
        this.preNode = preNode;
    }

    public Node<K, V> getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node<K, V> nextNode) {
        this.nextNode = nextNode;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(int indexValue) {
        this.indexValue = indexValue;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Node<K, V> getSetIterateNext() {
        return setIterateNext;
    }

    public void setSetIterateNext(Node<K, V> setIterateNext) {
        this.setIterateNext = setIterateNext;
    }

    @Override
    public String toString() {
        return "Node{" +
                "hashCode=" + hashCode +
                ", value=" + value +
                ", indexValue=" + indexValue +
                ", key=" + key +
                ", indexLength=" + indexLength +
                '}';
    }

    public int getIndexLength() {
        return indexLength;
    }

    public void setIndexLength(int indexLength) {
        this.indexLength = indexLength;
    }

    public Node(int hashCode, K key, int indexValue, V value, int indexLength) {
        this.hashCode = hashCode;
        this.value = value;
        this.indexValue = indexValue;
        this.key = key;
        this.indexLength = indexLength;
    }

}