package org.darkknight;

import java.util.*;

public class LinkedListOnes {

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {

        ListNode node11 = new ListNode(5, null);
        ListNode node12 = new ListNode(4, node11);
        ListNode node = new ListNode(4, node11);
        ListNode node1 = new ListNode(3, node);
        ListNode node2 = new ListNode(2, node1);
        ListNode node3 = new ListNode(1, node2);
        ListNode myList = new ListNode(1, node3);

        LinkedListOnes linked = new LinkedListOnes();
        // linked.swapNodes(node3);
        linked.reverseListIII(node3);

        ListNode res = linked.deleteDuplicatesII(myList);
        reorderList(myList);
    }

    public ListNode reverseListIII(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = reverseListIII(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }
    private static class ListNode{
        int val;
        ListNode next;
        ListNode(){

        }
        ListNode (int val1, ListNode node){
            val = val1;
            next = node;
        }
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    // it worked but check prev soln if that was better (had to hardcode dummy curr node)
    // https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/description/
    public ListNode deleteDuplicatesII(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode curr = new ListNode();
        curr.val = Integer.MIN_VALUE;
        curr.next = head;
        ListNode res = curr;
        ListNode prev = curr;
        while(curr != null){
            if(curr.next!= null &&  curr.val == curr.next.val){
                while(curr.next!= null && curr.val == curr.next.val){
                    curr = curr.next;
                }
                prev.next = curr.next;
            }else{
                prev = curr;
            }
            curr = curr.next;
        }
        return res.next;
    }

    // https://leetcode.com/problems/merge-two-sorted-lists/
    public ListNode mergeTwoLists(ListNode node1, ListNode node2) {
        if(node1 == null) return node2;
        if(node2 == null) return node1;

        ListNode res = new ListNode();
        ListNode head = res;
        while(node1!= null && node2!= null){
            if(node1.val < node2.val){
                res.next = node1;
                node1 = node1.next;
            }
            else{
                res.next = node2;
                node2 = node2.next;
            }
            res = res.next;
        }

        if(node1!= null){
            res.next = node1;
        } else if (node2 != null) {
            res.next = node2;
        }

        return head.next;
    }

    // #meta
    public static List<Integer> mergeThreeSortedLists(List<Integer> listA, List<Integer> listB, List<Integer> listC) {
        List<Integer> result = new ArrayList<>();
        int a = 0, b = 0, c = 0;
        int nA = listA.size(), nB = listB.size(), nC = listC.size();

        while (a < nA || b < nB || c < nC) {
            int aVal = (a < nA) ? listA.get(a) : Integer.MAX_VALUE;
            int bVal = (b < nB) ? listB.get(b) : Integer.MAX_VALUE;
            int cVal = (c < nC) ? listC.get(c) : Integer.MAX_VALUE;

            int minVal = Math.min(Math.min(aVal, bVal), cVal);
            result.add(minVal);

            if (a < nA && aVal == minVal) {
                a++;
            } else if (b < nB && bVal == minVal) {
                b++;
            } else {
                c++;
            }
        }

        return result;
    }

    // https://leetcode.com/problems/intersection-of-two-linked-lists/
    // traverse a list and set the head to other list head if it ends already
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;

        ListNode tmpA = headA;
        ListNode tmpB = headB;

        while(headA != headB){
            headA = headA == null ? tmpB : headA.next;
            headB = headB == null ? tmpA : headB.next;
        }

        return headA;
    }

    // I guess this was just for fun
    public ListNode getIntersectionNodeAlt(ListNode headA, ListNode headB) {
        if(headA == null && headB== null) return null;

        ListNode tempA = headA;
        ListNode tempB = headB;

        int sizeA = getLinkedListLength(tempA);
        int sizeB = getLinkedListLength(tempB);

        if(sizeA > sizeB){
            int count = sizeA-sizeB;
            while(count != 0){
                tempA = tempA.next;
                count--;
            }
        }else {
            int count = sizeB - sizeA;
            while (count != 0) {
                tempB = tempB.next;
                count--;
            }
        }
        while(tempA!= null || tempB!= null){
            if(tempA == tempB){
                return tempA;
            }else{
                tempA = tempA.next;
                tempB = tempB.next;
            }
        }

        return null;
    }

    private int getLinkedListLength(ListNode node){
        int size =0;
        while(node!= null){
            size++;
            node = node.next;
        }
        return size;
    }

    // https://leetcode.com/problems/remove-duplicates-from-sorted-list/description/
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode curr = head;

        while(curr!= null){
            if(curr.next != null && curr.val == curr.next.val){
                curr.next = curr.next.next;
            }
            else{
                curr = curr.next;
            }
        }

        return head;
    }

    // https://leetcode.com/problems/swap-nodes-in-pairs/
    public ListNode swapNodes(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode firstNode = head;
        ListNode secNode = head.next;

        firstNode.next = swapNodes(secNode.next);
        secNode.next = firstNode;
        return secNode;
    }

    // recursive version uses O(n)
    // this one uses O(1) extra space
    // https://leetcode.com/problems/swap-nodes-in-pairs/
    private ListNode swapPairs(ListNode head){
        if(head == null || head.next==null) return head;

        ListNode curr = head;
        ListNode next = curr.next;
        ListNode prev = null;
        ListNode newHead = next;

        while(curr!= null && next!= null){
            curr.next = next.next;
            next.next = curr;
            if(prev != null){
                prev.next = next;
            }

            // adjust pointers
            prev = curr;
            curr = curr.next;
            if(curr != null){
                next = curr.next;
            }
        }

        return newHead;
    }

    // https://leetcode.com/problems/remove-linked-list-elements
    public ListNode removeElements(ListNode head, int val) {
        if(head == null) return head;
        ListNode curr = new ListNode();
        curr.next = head;

        ListNode tmp = curr;
        while(curr!= null){
            if(curr.next != null && curr.next.val == val){
                curr.next = curr.next.next;
            }
            else{
                curr = curr.next;
            }
        }
        return tmp.next;
    }

    // https://leetcode.com/problems/linked-list-cycle-ii/
    public ListNode linkedListCycleII(ListNode head) {
        if(head == null) return head;
        if(head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        while(fast!= null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast) break;
        }

        if(slow != fast) return null;

        slow = head;

        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    public TreeNode treeToDoublyLinkedList(TreeNode root){
        if(root == null) return null;
        Stack<TreeNode> stack = new Stack<>();

        TreeNode curr = root;
        TreeNode start = root;
        while(start.left!= null){
            start = start.left;
        }

        TreeNode prev = null;

        while(curr!= null || !stack.isEmpty()){
            while (curr!= null){
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            if(prev!= null){
                prev.right = curr;
                curr.left = prev;
            }

            prev = curr;
            curr = curr.right;
        }

        start.left = prev;
        prev.right = start;

        return start;
    }

    // https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list
    public ListNode removeZeroSumSublists(ListNode head) {
        int sumSoFar = 0;

        ListNode newHead = new ListNode();
        newHead.next = head;
        ListNode curr = newHead;
        Map<Integer, ListNode> map = new HashMap<>();

        while(curr != null){
            sumSoFar += curr.val;

            if(map.containsKey(sumSoFar)){
                ListNode prev = map.get(sumSoFar);
                curr = prev.next;

                int p = sumSoFar + curr.val;

                // Delete zero sum nodes from hashmap
                // to prevent incorrect deletions from linked list
                while(p!= sumSoFar){
                    map.remove(p);
                    curr = curr.next;
                    p += curr.val;
                }
                prev.next = curr.next;
            }else{
                map.put(sumSoFar, curr);
            }
            curr = curr.next;
        }
        return newHead.next;
    }

    // 1 -> 2 -> 3 -> 4 -> 5
    // https://leetcode.com/problems/odd-even-linked-list
    public ListNode oddEvenList(ListNode head) {
        if(head == null) return head;

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while(even!= null && even.next!= null){
            odd.next = even.next;
            even.next = odd.next.next;

            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }

    //https://leetcode.com/problems/sort-list/description/
    // merge sort
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode prev = null, slow = head, fast = head;
        while(fast!= null && fast.next!= null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        return mergeTwoLists(l1, l2);
    }

    // https://leetcode.com/problems/palindrome-linked-list/description/
    public boolean palindromeLinkedList(ListNode head) {
        if(head == null || head.next == null) return true;

        ListNode slow = head;
        ListNode fast = head;

        while(fast!= null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
        }

        if(fast!= null){
            slow = slow.next;
        }
        fast = reverseList(slow);
        slow = head;

        while(fast!= null){
            if(fast.val != slow.val) return false;
            slow = slow.next;
            fast = fast.next;
        }

        return true;
    }

    // https://leetcode.com/problems/palindrome-linked-list/description/
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null) return true;

        ListNode slow= head;
        ListNode fast = head;

        Stack<ListNode> stack = new Stack<>();
        while(fast!= null && fast.next!= null){
            stack.push(slow);
            slow = slow.next;
            fast = fast.next.next;
        }
        if(fast!= null){
            slow = slow.next;
        }

        while(!stack.isEmpty() || slow!= null){
            if(stack.pop().val != slow.val) return false;
            slow = slow.next;
        }

        return stack.isEmpty() && slow == null;
    }


    // https://leetcode.com/problems/reverse-nodes-in-k-group/
    public ListNode reverseKGroupOrg(ListNode head, int k) {
        ListNode ptr = head;
        ListNode newHead = null;
        ListNode kTail = null;

        while(ptr != null){
            int count =0;

            ptr = head;

            while(count < k && ptr!= null){
                ptr = ptr.next;
                count += 1;
            }

            if(count == k){
                ListNode revHead = reverseKList(head, k);

                if(newHead == null){
                    newHead = revHead;
                }

                if (kTail != null)
                    kTail.next = revHead;

                kTail = head;
                head = ptr;
            }

            if(kTail != null){
                kTail.next = head;
            }
        }

        return newHead == null ? head : newHead;
    }

    private ListNode reverseKList(ListNode head, int k){
        ListNode prev = null;
        ListNode curr = head;

        while(k > 0){
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
            k--;
        }
        return prev;
    }

    // https://leetcode.com/problems/reverse-linked-list-ii/description/
    // same as rev just take 2 add pointers for conn and tail to join later
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if(head == null || left == right) return head;

        ListNode curr = head;
        ListNode prev = null;
        while(left > 1){
            prev = curr;
            curr = curr.next;
            left--;
            right--;
        }

        ListNode conn = prev;
        ListNode tail = curr;

        while(right > 0){
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
            right--;
        }

        if(conn!= null){
            conn.next = prev;
        }else{
            head = prev;
        }

        tail.next = curr;
        return head;
    }

    // https://leetcode.com/problems/rotate-list/
    public ListNode rotateList(ListNode head, int k) {
        if(head == null || head.next == null) return head;

        ListNode tmp = new ListNode();
        tmp.next = head;

        ListNode fast = tmp;
        ListNode slow = tmp;

        int len = 0;
        while(fast.next != null){
            len++;
            fast = fast.next;
        }

        int pointOfRotation = len - k%len;

        for(int i =0; i<pointOfRotation; i++){
            slow = slow.next;
        }

        fast.next = tmp.next;
        tmp.next = slow.next;
        slow.next = null;

        return tmp.next;
    }

    // https://leetcode.com/problems/reverse-nodes-in-k-group/
    // This is recursive
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;

        while(curr != null && count != k){
            curr = curr.next;
            count++;
        }

        if(count == k){
            curr = reverseKGroup(curr, k);

            while(count-- > 0){
                ListNode tmp = head.next;
                head.next = curr;
                curr = head;
                head = tmp;
            }
            head = curr;
        }
        return head;
    }


    // https://leetcode.com/problems/merge-k-sorted-lists/
    public ListNode mergeKSortedLists(ListNode[] lists) {
        if(lists == null || lists.length ==0) return null;
        ListNode res = new ListNode();

        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode node : lists) {
            if(node != null) pq.offer(node);
        }

        ListNode prev = res;
        while(!pq.isEmpty()){
            ListNode curr = pq.poll();
            prev.next = curr;
            prev = curr;

            if(curr.next != null) pq.offer(curr.next);
        }

        return res.next;
    }

    // merge-k-sorted-lists/
    // just to avoid log-K extra space, we can merge 2 at a time and then merge next 2 which were merged in prev step
    // and keep going
    public ListNode mergeKLists(ListNode[] lists) {
        int amount = lists.length;
        int interval = 1;
        while (interval < amount) {
            for (int i = 0; i < amount - interval; i += interval * 2) {
                lists[i] = merge2Lists(lists[i], lists[i + interval]);
            }
            interval *= 2;
        }
        return amount > 0 ? lists[0] : null;
    }

    public ListNode merge2Lists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0, null);
        ListNode point = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                point.next = l1;
                l1 = l1.next;
            } else {
                point.next = l2;
                l2 = l1;
                l1 = point.next.next;
            }
            point = point.next;
        }
        if (l1 == null) {
            point.next = l2;
        } else {
            point.next = l1;
        }
        return head.next;
    }

    // https://leetcode.com/problems/find-the-duplicate-number/
    public int findDuplicate(int[] nums) {
        int slow = nums[0];
        int fast = nums[nums[0]];

        while(slow != fast){
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        slow = 0;
        while(slow != fast){
            slow = nums[slow];
            fast = nums[fast];
        }
        return fast;
    }

    // https://leetcode.com/problems/add-two-numbers/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;

        ListNode prev = new ListNode();
        ListNode head = prev;

        int carry = 0;
        while(l1 != null || l2 != null || carry!= 0){
            int value = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + carry;
            int val = value%10;
            carry = value/10;

            ListNode curr = new ListNode(val, null);
            prev.next = curr;
            prev = curr;

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        return head.next;
    }

    // don't forget have to reverse the output as well
    // https://leetcode.com/problems/add-two-numbers-ii/description/
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2 == null) return l1;

        l1 = reverseList(l1);
        // print(l1);
        l2 = reverseList(l2);

        ListNode head = null;
        int carry = 0;
        while(l1!= null || l2!= null){
            int sum1 = l1 != null ? l1.val : 0;
            int sum2 = l2 != null ? l2.val : 0;

            int sum = carry + (sum1+sum2);
            int newVal = sum%10;
            carry = (sum)/10;

            ListNode curr = new ListNode(newVal, null);
            if(head == null) {
                head = curr;
            }else{
                curr.next = head;
                head = curr;
            }

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }

        if(carry != 0){
            ListNode curr = new ListNode(carry, null);
            curr.next = head;
            head = curr;
        }

        return head;
    }

    // https://leetcode.com/problems/linked-list-cycle/description/
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast) return true;
        }
        return false;
    }

    // https://leetcode.com/problems/middle-of-the-linked-list/
    public ListNode middleNode(ListNode head) {
        if(head == null) return head;

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // https://leetcode.com/problems/copy-list-with-random-pointer/
    public Node copyRandomList(Node head) {
        if(head == null) return head;

        Node tmp = head;
        while(tmp!= null){
            Node node = new Node(tmp.val);
            node.next = tmp.next;
            tmp.next = node;
            tmp = tmp.next.next;
        }

        tmp = head;
        while(tmp!= null){
            Node node = tmp.next;
            node.random = tmp.random == null ? null : tmp.random.next;
            tmp = tmp.next.next;
        }

        // dis-associate
        tmp = head;
        Node newHead = tmp.next;
        Node newTmp = newHead;

        while(newTmp != null){
            tmp.next = tmp.next.next;
            newTmp.next = newTmp.next == null ? null : newTmp.next.next;

            tmp = tmp.next;
            newTmp = tmp == null ? null : tmp.next;
        }

        return newHead;
    }

    // https://leetcode.com/problems/reorder-list/
    private static void reorderList(ListNode head) {
        if(head == null || head.next == null) return;

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode node1 = head;
        ListNode node2 = reverseList(slow);
        ListNode tmp = null;

        while(node2.next != null) {
            tmp = node1.next;
            node1.next = node2;
            node1 = tmp;

            tmp = node2.next;
            node2.next = node1;
            node2 = tmp;
        }
    }

    //  https://leetcode.com/problems/remove-nth-node-from-end-of-list/
    private ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tmp = new ListNode();
        tmp.next = head;

        ListNode slow = tmp;
        ListNode fast = tmp;
        for (int i = 0; i<=n; i++ ) {
            fast  = fast.next;
        }
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
        }

        slow.next = slow.next.next;
        return tmp.next;
    }

    // https://leetcode.com/problems/reverse-linked-list/
    private static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode prev = null;

        while(head != null){
            ListNode temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;
        }

        return prev;
    }

    // https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list
    public ListNode insertInCircularList(ListNode head, int insertVal) {
        if(head == null){
            ListNode newNode = new ListNode(insertVal, null);
            newNode.next = newNode;
            return newNode;
        }

        ListNode node = head;
        while(node.next != head){
            if(node.val <= node.next.val){
                // found a place to insert in between
                if(insertVal >= node.val && insertVal <= node.next.val){
                    break;
                }
            }
            else{
                // inserting at beginning or end
                if(insertVal >= node.val || insertVal <= node.next.val){
                    break;
                }
            }
            node = node.next;
        }

        ListNode next = node.next;
        node.next = new ListNode(insertVal, next);
        return head;
    }
}

