package com.zms.collection.domain;

import java.util.Comparator;

/**
 * @Description : TODO
 * @author: zeng.maosen
 * @date: 2023/2/13
 * @version: 1.0
 */
public class LinkNode<V> {
    private LinkNode<V> preNode;
    private LinkNode<V> nextNode;
    private V value;


    public LinkNode<V> getPreNode() {
        return preNode;
    }

    public void setPreNode(LinkNode<V> preNode) {
        this.preNode = preNode;
    }

    public LinkNode<V> getNextNode() {
        return nextNode;
    }

    public void setNextNode(LinkNode<V> nextNode) {
        this.nextNode = nextNode;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public LinkNode(V value, Comparable<V> comparable) {
        this.value = value;
    }

    public LinkNode(V value) {
        this.value = value;
    }
}
