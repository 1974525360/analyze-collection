package com.zms.collection;

import com.zms.collection.domain.LinkNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author zeng.maosen
 * @Description TODO
 * @Date 2023/02/10/22:08
 * @Version 1.0
 */
public class LinkList<V> {
    private LinkNode<V> head;
    private final Comparator<? super V> comparator;
    private final List<V> list;


    public LinkList(Comparator<? super V> comparator) {
        this.comparator = comparator;
        list = new ArrayList<>();
    }

    public LinkList() {
        comparator = null;
        list = new ArrayList<>();
    }

    void addNode(LinkNode<V> head, LinkNode<V> node){
        if(head==null|| node==null){
            return;
        }
        LinkNode<V> cur = head;
        while (cur.getNextNode() != null) {
            cur = cur.getNextNode();
        }
        cur.setNextNode(node);
        node.setPreNode(cur);
    }

    LinkNode<V> insertNode(LinkNode<V> head, LinkNode<V> node){
        if(head==null|| node==null){
            return head;
        }
        if(comparator==null){
            addNode(head,node);
        }
        LinkNode<V> cur=head;
        while (cur!=null){
            int compare = compare(node.getValue(), cur.getValue());
            if(compare>0){
                break;
            }
            cur = cur.getNextNode();
        }
        if(cur==head){
            node.setNextNode(cur);
            cur.setPreNode(node);
        }else if(cur==null){
            addNode(head,node);
        }else{
            LinkNode<V> next= cur.getNextNode();
            if(next!=null) {
                next.setPreNode(node);
            }
            node.setNextNode(next);
            cur.setNextNode(node);
            node.setPreNode(cur);
        }
        return head.getPreNode();
    }


    LinkNode<V> margeNode(LinkNode<V> node){
        LinkNode<V> showNode = node;
        LinkNode<V> fastNode = node.getNextNode();

        //找寻快慢节点
        while(fastNode!=null&&fastNode.getNextNode()!=null){
            showNode = showNode.getNextNode();
            fastNode = fastNode.getNextNode();
            if(fastNode.getNextNode()!=null){
                fastNode = fastNode.getNextNode();
            }
        }
        LinkNode<V> next = showNode.getNextNode();
        showNode.setNextNode(null);
        return next;
    }

    LinkNode<V>  margeSortNode(LinkNode<V> node){
        if(node==null || node.getNextNode()==null){
            return node;
        }
        LinkNode<V> head1 = node;
        LinkNode<V> head2 = margeNode(node);
        head1 = margeSortNode(head1);
        head2 = margeSortNode(head2);
        return margeSort(head1,head2);
    }

   LinkNode<V> margeSort(LinkNode<V> node1,LinkNode<V> node2){
        LinkNode<V> node =new LinkNode<V>(null);
        LinkNode<V> cur1= node1;
        LinkNode<V> cur2= node2;
        LinkNode<V> result = node;
        while(cur1!=null&&cur2!=null){
            if(compare(node1,node2)>0){
                result.setNextNode(cur2);
                cur2.setPreNode(result);
                cur2 = cur2.getNextNode();
            }else{
                result.setNextNode(cur1);
                cur1.setPreNode(result);
                cur1 = cur1.getNextNode();
            }
            result = result.getNextNode();
        }
        if(cur1!=null){
            result.setNextNode(cur1);
        }
        if(cur2!=null){
            result.setNextNode(cur2);
        }
        return node.getNextNode();
    }



    final int compare(Object k1, Object k2) {
        return comparator==null ? ((Comparable<? super V>)k1).compareTo((V)k2)
                : comparator.compare((V)k1, (V)k2);
    }

}
