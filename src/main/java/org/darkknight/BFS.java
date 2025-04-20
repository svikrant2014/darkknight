package org.darkknight;

import java.util.*;

public class BFS {
    public static void main(String[] args) {

    }
    private class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    };

    // https://leetcode.com/problems/employee-importance/
    public int getImportance(List<Employee> employees, int id){
        int total = 0;
        HashMap<Integer, Employee> map = new HashMap<>();

        for (Employee employee: employees) {
            map.put(employee.id, employee);
        }

        Queue<Employee> queue = new LinkedList<>();
        queue.offer(map.get(id));

        while(!queue.isEmpty()){
            Employee curr = queue.poll();
            total += curr.importance;

            for (int sub : curr.subordinates) {
                queue.offer(map.get(sub));
            }
        }

        return total;
    }


    // https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero
    public int minReorder(int n, int[][] connections) {
        Set<String> paths = new HashSet<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        for(int[] conn : connections){
            paths.add(conn[0] + ":" + conn[1]);
            map.computeIfAbsent(conn[0], k-> new ArrayList<>());
            map.computeIfAbsent(conn[1], k-> new ArrayList<>());

            map.get(conn[0]).add(conn[1]);
            map.get(conn[1]).add(conn[0]);
        }

        int changes = 0;
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited.add(0);

        while(!queue.isEmpty()){
            int city = queue.remove();

            for(int next : map.getOrDefault(city, new ArrayList<>())){
                if(visited.contains(next)) continue;

                visited.add(next);
                queue.offer(next);
                if(!paths.contains(next + ":" + city)){
                    changes++;
                }
            }
        }
        return changes;
    }
}
