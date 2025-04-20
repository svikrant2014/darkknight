package org.darkknight;

public class Recursion {
    static class Node{
        int val;
        Node next;
    }

    public static void main(String[] args) {
        Recursion rr = new Recursion();

        Node head = new Node();
        head.val  =1;

        Node node1 = new Node();
        node1.val = 2;
        head.next = node1;

        Node node2 = new Node();
        node2.val = 3;
        node1.next = node2;

        Node node3 = new Node();
        node3.val = 4;
        node2.next = node3;
        head = rr.swapNodesII(head);

//        rr.reverseString(new char[]{'v', 'i'});
//        rr.printReverse("checking");
        //rr.solve(7);
    }

    // 1 -> 2 -> 3 -> 4 -> 5
    private Node swapNodes(Node head){
        if(head == null || head.next == null) return head;

        Node firstNode = head;
        Node secNode = head.next;

        firstNode.next = swapNodes(secNode.next);
        secNode.next = firstNode;
        return secNode;
    }

    private Node swapNodesII(Node head){
        if(head == null || head.next == null) return head;

        Node curr = head;
        Node next = curr.next;
        Node prev = null;
        Node newHead = next;

        while(curr != null && next!= null){
            curr.next = next.next;
            next.next = curr;
            if(prev!= null){
                prev.next = next;
            }

            prev = curr;
            curr = curr.next;
            if(curr!= null){
                next = curr.next;
            }
        }
        return newHead;
    }

    private void swap(Node head){
        if(head == null) return;

        Node tmp = head;
        head = head.next;
        head.next = tmp;
        swap(head.next.next);
    }


    private void reverseString(char[] str){
        helper2(str, 0, str.length-1);
    }

    private void helper2(char[] arr, int lo, int hi){
        if(arr == null || lo >= hi) return;

        char tmp = arr[lo];
        arr[lo] = arr[hi];
        arr[hi] = tmp;
        helper2(arr, lo+1, hi-1);
    }

    // Test
    private void printReverse(String str){
        helper(str, 0);
    }

    private void helper(String arr, int index){
        if(arr == null || index >= arr.length() ) return;

        helper(arr, index+1);
        System.out.println(arr.charAt(index));
    }

    public void solve(int n){
        if(n==0) return;
        System.out.println(n);
        solve(n-1);
    }
}
