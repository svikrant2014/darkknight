package org.darkknight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

// https://leetcode.com/problems/reconstruct-itinerary/
// Eulerian path traversal (cover every edge atleast once, may visit vertices multiple times)
// Hierholzer's algorithm
// problem asks for lexical order smallest solution, we can put the neighbors in a min-heap
public class ReconstructItinerary {

    static HashMap<String, PriorityQueue<String>> map;
    static LinkedList<String> res;

    public static List<String> findItinerary(List<List<String>> tickets, String startAt) {
        map = new HashMap<>();
        res = new LinkedList<>();

        // build the graph
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);

            if (!map.containsKey(from)) map.put(from, new PriorityQueue<>());
            map.get(from).offer(to);
        }

        dfs(startAt);
        return res;
    }

    private static void dfs(String from) {
        PriorityQueue<String> arrivals = map.get(from);
        while (arrivals != null && !arrivals.isEmpty()) {
            dfs(arrivals.poll());
        }
        res.addFirst(from);
    }

    public static void main(String[] args) {
        List<List<String>> input = new ArrayList<>();
        //[["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
        List<String> route = new ArrayList<>();
        route.add("JFK");
        route.add("SFO");
        input.add(route);

        route = new ArrayList<>();
        route.add("JFK");
        route.add("ATL");
        input.add(route);

        route = new ArrayList<>();
        route.add("SFO");
        route.add("ATL");
        input.add(route);

        route = new ArrayList<>();
        route.add("ATL");
        route.add("JFK");
        input.add(route);

        route = new ArrayList<>();
        route.add("ATL");
        route.add("SFO");
        input.add(route);

        findItinerary(input, "JFK");
    }
}