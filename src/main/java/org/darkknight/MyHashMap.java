package org.darkknight;

// https://leetcode.com/problems/design-hashmap
public class MyHashMap {
    private class ListNode{
        int key, val;
        ListNode next;

        ListNode(int key, int val){
            this.key = key;
            this.val = val;
        }
    }

    final ListNode[] nodes = new ListNode[10000];
    public MyHashMap() {

    }

    public void put(int key, int value) {
        int idx = index(key);
        if(nodes[idx] == null){
            nodes[idx] = new ListNode(-1, -1);
        }

        ListNode prev = find(nodes[idx], key);
        if(prev.next == null){
            prev.next = new ListNode(key, value);
        }
        else{
            prev.next.val = value;
        }
    }

    public int get(int key) {
        int i = index(key);
        if(nodes[i] == null) return -1;
        ListNode node = find(nodes[i], key);
        return node.next == null ? -1 : node.next.val;
    }

    public void remove(int key) {
        int i = index(key);
        if(nodes[i] == null) return;
        ListNode node = find(nodes[i], key);
        if(node.next == null) return;
        node.next = node.next.next;
    }

    private int index(int key){
        return Integer.hashCode(key) % nodes.length;
    }

    private ListNode find(ListNode bucket, int key){
        ListNode node = bucket, prev = null;
        while(node != null && node.key != key){
            prev = node;
            node = node.next;
        }
        return prev;
    }
}
