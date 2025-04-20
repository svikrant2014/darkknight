package org.darkknight;

import java.util.HashMap;

// https://leetcode.com/problems/lru-cache/description/
public class LRUCache {
    class DNode{
        int key;
        int val;
        DNode next;
        DNode prev;
    }

    HashMap<Integer, DNode> cache = new HashMap<>();
    DNode head, tail;
    int cap;
    int count;

    public LRUCache(int capacity) {
        cap = capacity;
        count = 0;
        head = new DNode();
        head.prev = null;

        tail = new DNode();
        tail.next = null;
        tail.prev =head;
        head.next = tail;
    }

    public int get(int key) {
        DNode selected = cache.get(key);
        if(selected != null){
            removeNode(selected);
            return selected.val;
        }
        return -1;
    }

    public void put(int key, int value) {
        DNode node = cache.get(key);
        if(node == null){
            // add to the cache
            // check cap then pop LRU
            // move to the top of list
            DNode newNode = new DNode();
            newNode.key = key;
            newNode.val = value;
            count++;

            cache.put(key, newNode);
            addNewNode(newNode);
            if(count > cap){
                DNode tmp = popTail();
                cache.remove(tmp);
                count--;
            }
        }
        else{
            node.val =value;
            moveToHead(node);
        }
    }

    private void addNewNode(DNode newNode){
        newNode.next = head.next;
        newNode.prev = head;

        head.next.prev = newNode;
        head.next = newNode;
    }

    private void removeNode(DNode node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DNode node){
        removeNode(node);
        addNewNode(node);
    }

    private DNode popTail(){
        DNode tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }
}
