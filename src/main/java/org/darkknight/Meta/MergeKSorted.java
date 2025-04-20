package org.darkknight.Meta;
import java.util.*;

public class MergeKSorted {

    // #metas
    private static class Element implements Comparable<Element> {
        int listIndex;
        int elementIndex;
        int value;

        public Element(int listIndex, int elementIndex, int value) {
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
            this.value = value;
        }

        @Override
        public int compareTo(Element other) {
            return Integer.compare(this.value, other.value); // Min-Heap: smaller value has higher priority
        }
    }

    private PriorityQueue<Element> minHeap;
    private List<List<Integer>> lists;

    public MergeKSorted(List<List<Integer>> lists) {
        this.lists = lists;
        this.minHeap = new PriorityQueue<>();

        for (int i = 0; i < lists.size(); i++) {
            if (!lists.get(i).isEmpty()) {
                minHeap.offer(new Element(i, 0, lists.get(i).get(0)));
            }
        }
    }

    public boolean hasNext() {
        return !minHeap.isEmpty();
    }

    public int next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements left");
        }

        Element current = minHeap.poll();
        int listIndex = current.listIndex;
        int elementIndex = current.elementIndex;
        int value = current.value;

        // Move to the next element in the same list
        elementIndex++;
        if (elementIndex < lists.get(listIndex).size()) {
            minHeap.offer(new Element(listIndex, elementIndex, lists.get(listIndex).get(elementIndex)));
        }

        return value;
    }

    // merge-k-sorted-arrays #meta
    public static List<Integer> mergeKSortedArrays(List<List<Integer>> arrays) {
        List<Integer> result = new ArrayList<>();

        // Min-Heap to store (value, arrayIndex, elementIndex)
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // Initialize the heap with the first element of each array
        for (int i = 0; i < arrays.size(); i++) {
            if (!arrays.get(i).isEmpty()) {
                minHeap.offer(new int[] {arrays.get(i).get(0), i, 0});
            }
        }

        // Merge all arrays
        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int value = current[0];
            int arrayIndex = current[1];
            int elementIndex = current[2];

            result.add(value);

            // Add next element from the same array
            if (elementIndex + 1 < arrays.get(arrayIndex).size()) {
                minHeap.offer(new int[] {arrays.get(arrayIndex).get(elementIndex + 1), arrayIndex, elementIndex + 1});
            }
        }

        return result;
    }

}
