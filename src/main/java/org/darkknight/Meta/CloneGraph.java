package org.darkknight.Meta;

import java.util.*;

class Node {
    public int val;
    public List<Node> neighbors;

    public Node(int val) {
        this.val = val;
        this.neighbors = new ArrayList<>();
    }
}

class Graph {
    public List<Node> roots;

    public Graph() {
        this.roots = new ArrayList<>();
    }
}

public class CloneGraph {
    // #meta
    // clone a "disconnected" graph ... regular one is connected (check carefully)
    // actually DFS sounds easy and better ... just take all neighbors and call on each root of each disconnected components
    // and add it to result graph
    // will save BFS too in the end... coz you never know
    public Graph cloneGraph(Graph input) {
        if (input == null) return null;

        Map<Node, Node> cloneMap = new HashMap<>();
        Graph clonedGraph = new Graph();

        for (Node root : input.roots) {
            if(root == null) continue; // extra check if a disconnected comp is null in input
            if (!cloneMap.containsKey(root)) {
                clonedGraph.roots.add(cloneDFS(root, cloneMap));
            }
        }

        return clonedGraph;
    }

    private Node cloneDFS(Node node, Map<Node, Node> cloneMap) {
        if (node == null) return null;

        // If already cloned, return the cloned reference
        if (cloneMap.containsKey(node)) {
            return cloneMap.get(node);
        }

        // Create new clone node
        Node clone = new Node(node.val);
        cloneMap.put(node, clone);

        // Clone all neighbors recursively
        for (Node neighbor : node.neighbors) {
            clone.neighbors.add(cloneDFS(neighbor, cloneMap));
        }

        return clone;
    }

    private Node cloneBFS(Node node, Map<Node, Node> cloneMap) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        Node clone = new Node(node.val);
        cloneMap.put(node, clone);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (Node neighbor : current.neighbors) {
                if (!cloneMap.containsKey(neighbor)) {
                    cloneMap.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                cloneMap.get(current).neighbors.add(cloneMap.get(neighbor));
            }
        }

        return clone;
    }
}
