package org.darkknight.Meta;

import java.util.*;

public class DisjointUnion {

    // https://leetcode.com/problems/accounts-merge
    // https://leetcode.com/problems/accounts-merge/solutions/109157/java-c-union-find/?envType=company&envId=facebook&favoriteSlug=facebook-thirty-days
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, String> owner = new HashMap<>();
        Map<String, String> parents = new HashMap<>();
        Map<String, TreeSet<String>> unions = new HashMap<>();
        for (List<String> a : accounts) {
            for (int i = 1; i < a.size(); i++) {
                parents.put(a.get(i), a.get(i));
                owner.put(a.get(i), a.get(0));
            }
        }
        for (List<String> a : accounts) {
            String p = find(a.get(1), parents);
            for (int i = 2; i < a.size(); i++)
                parents.put(find(a.get(i), parents), p);
        }
        for(List<String> a : accounts) {
            String p = find(a.get(1), parents);
            if (!unions.containsKey(p)) unions.put(p, new TreeSet<>());
            for (int i = 1; i < a.size(); i++)
                unions.get(p).add(a.get(i));
        }
        List<List<String>> res = new ArrayList<>();
        for (String p : unions.keySet()) {
            List<String> emails = new ArrayList(unions.get(p));
            emails.add(0, owner.get(p));
            res.add(emails);
        }
        return res;
    }

    private String find(String s, Map<String, String> p) {

        return p.get(s) == s ? s : find(p.get(s), p);
    }

    //
    private void dfs(Map<String, List<String>> adjs,
                     Map<String, String> emailToId,
                     Set<String> visited,
                     String currEmail,
                     String id) {
        visited.add(currEmail);
        emailToId.put(currEmail, id);
        for (String adj : adjs.getOrDefault(currEmail, new ArrayList<>())) {
            if (!visited.contains(adj)) {
                dfs(adjs, emailToId, visited, adj, id);
            }
        }
    }

    // // VARIANT: What if you were given the input as a map from an ID to a list of corresponding emails?
    //// Furthermore, you have to return a 2D list of all of the same IDs.
    public List<List<String>> accountsMergeMeta(Map<String, List<String>> accounts) {
        Map<String, List<String>> adjs = new HashMap<>();

        // Build the adjacency list
        for (Map.Entry<String, List<String>> entry : accounts.entrySet()) {
            String id = entry.getKey();
            List<String> emails = entry.getValue();
            String firstEmail = emails.get(0);

            for (int i = 1; i < emails.size(); i++) {
                adjs.computeIfAbsent(firstEmail, k -> new ArrayList<>()).add(emails.get(i));
                adjs.computeIfAbsent(emails.get(i), k -> new ArrayList<>()).add(firstEmail);
            }
        }

        Map<String, String> emailToId = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : accounts.entrySet()) {
            String id = entry.getKey();
            List<String> emails = entry.getValue();
            String firstEmail = emails.get(0);

            if (visited.contains(firstEmail)) {
                String sameId = emailToId.get(firstEmail);
                result.computeIfAbsent(sameId, k -> new ArrayList<>()).add(id);
            } else {
                result.put(id, new ArrayList<>());
                dfs(adjs, emailToId, visited, firstEmail, id);
            }
        }

        List<List<String>> resultV2 = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            List<String> group = new ArrayList<>();
            group.add(entry.getKey());
            group.addAll(entry.getValue());
            resultV2.add(group);
        }

        return resultV2;
    }
}
