package org.darkknight;

import java.util.*;

public class GraphBased {
    private static class UndirectedGraphNode {
        public int val;
        public List<UndirectedGraphNode> neighbors;
        public UndirectedGraphNode() {
            val = 0;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
        public UndirectedGraphNode(int _val) {
            val = _val;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
        public UndirectedGraphNode(int _val, ArrayList<UndirectedGraphNode> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    public static void main(String[] args) {

    }

    // https://leetcode.com/problems/clone-graph/description/
    // TC - O(n+e), SC - O(n)
    public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node){
        if(node == null) return null;
        HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
        UndirectedGraphNode newNode = new UndirectedGraphNode(node.val);
        map.put(node, newNode);

        Queue<UndirectedGraphNode> queue = new LinkedList<>();
        queue.offer(node);

        while(!queue.isEmpty()){
            UndirectedGraphNode curr = queue.poll();

            for(UndirectedGraphNode neigh : curr.neighbors) {
                if (!map.containsKey(neigh)) {
                    map.put(neigh, new UndirectedGraphNode(neigh.val));
                    queue.offer(neigh);
                }
                map.get(curr).neighbors.add(map.get(neigh));
            }
        }

        return newNode;
    }

    // DFS clone-graph just in case
    private static UndirectedGraphNode dfs(UndirectedGraphNode node, Map<UndirectedGraphNode, UndirectedGraphNode> map) {
        if (map.containsKey(node)) {
            return map.get(node);
        }

        UndirectedGraphNode clone = new UndirectedGraphNode(node.val);
        map.put(node, clone);

        for (UndirectedGraphNode neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor, map));
        }

        return clone;
    }

    // has to be a DAG (directed acyclic graph)
    // check for courses which has no dependencies and start finishing them
    // https://leetcode.com/problems/course-schedule/
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] indegress =new int[numCourses];

        for(int[] rel : prerequisites){
            map.computeIfAbsent(rel[1], o -> new ArrayList<>()).add(rel[0]);
            ++indegress[rel[0]];
        }

        Queue<Integer> queue = new LinkedList<>();
        for(int i=0; i<numCourses; i++){
            if(indegress[i] == 0){
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()){
            int size = queue.size();

            for(int i=size; i>0; i--){
                int curr = queue.poll();
                --numCourses;

                if(!map.containsKey(curr)) continue;

                for(int course : map.remove(curr)){
                    --indegress[course];
                    if(indegress[course] == 0){
                        queue.offer(course);
                    }
                }
            }
        }
        return numCourses == 0 ? true : false;
    }
    // https://leetcode.com/problems/course-schedule-ii/
    // O(V+E)
    public static int[] canFinishAllCoursesII(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegrees = new int[numCourses];

        // populate graph and count inDegrees
        for (int[] relation : prerequisites) {
            graph.computeIfAbsent(relation[1], l -> new ArrayList<>()).add(relation[0]);
            ++inDegrees[relation[0]]; // means course 1 has dependency on 0
        }

        // add all inDegree 0 to queue to start with BFS
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegrees[i] == 0)
                queue.offer(i);
        }

        int[] arr = new int[numCourses];
        int order = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = size; i > 0; i--) {
                int c = queue.poll();
                arr[order++] = c;
                --numCourses; // reduce the course as we just took one

                if (!graph.containsKey(c))
                    continue; // c's in-degree is currently 0, but it has no prerequisite

                for (int course : graph.remove(c)) { // get all neighbors of c
                    --inDegrees[course]; // reduce the dependencies of this course
                    if (inDegrees[course] == 0)
                        queue.offer(
                                course); // and if it's inDegree is 0, means ready to be taken so add it to the Q
                }
            }
            // ++semester;
        }

        return numCourses == 0 ? arr : new int[0]; // empty array -> new int[0]
    }

    // https://leetcode.com/problems/word-ladder/description/
    // TC: O(M^2 * N),where M is size of dequeued word & N is size of our word list
    // SC -  BigO(M * N) where M is no. of character that we had in our string & N is the size of our wordList.
    public int wordladder(String beginWord, String endWord,  List<String> wordList){
        HashSet<String> set = new HashSet<>();
        set.addAll(wordList);
        if(!set.contains(endWord)) return 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int level = 1;

        while(!queue.isEmpty()){
            int size = queue.size();
            level++;

            for(int i =0; i<size; i++){
                String curr = queue.poll();

                for(int j =0; j<curr.length(); j++){
                    char[] tmp = curr.toCharArray();

                    for(char c = 'a'; c <='z'; c++){
                       if(tmp[j] == c) continue;

                       char orig = tmp[j];
                       tmp[j] = c;

                       String newWord = String.valueOf(tmp);
                       if(endWord.equals(newWord)) return level++;

                       if(set.contains(newWord)){
                           queue.offer(newWord);
                           set.remove(newWord);
                       }

                       tmp[j] = orig;
                    }
                }
            }
        }
        return 0;
    }

    // https://leetcode.com/problems/parallel-courses
    public int minimumSemesters(int n, int[][] relations) {
        List<List<Integer>> graph = new ArrayList<>(n);
        int[] inDegree = new int[n + 1];
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for(int[] r: relations) {
            graph.get(r[0]).add(r[1]);
            inDegree[r[1]]++;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for(int i = 1; i <= n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int semester = 0;
        while(!queue.isEmpty()){
            for(int q = queue.size(); q > 0; q--){
                int course = queue.poll();
                n--;
                for(int adj: graph.get(course)) {
                    if (--inDegree[adj] == 0) {
                        queue.offer(adj);
                    }
                }
            }
            semester++;
        }
        return n == 0 ? semester : -1;
    }

}
