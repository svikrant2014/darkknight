package org.darkknight;

public class WordDictionary {

    TrieNode root;

    /** Initialize your data structure here. */
    public WordDictionary() {
        this.root = new TrieNode();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        TrieNode node = root;
        for(char ch : word.toCharArray()){
            if (node.child[ch - 'a'] == null){
                node.child[ch - 'a'] = new TrieNode();
            }
            node = node.child[ch - 'a'];
        }
        node.isWord = true;
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        return search(word, this.root, 0);
    }

    private boolean search(String word, TrieNode node, int index){

        if (index == word.length()) return node.isWord;

        if(word.charAt(index) != '.'){
            return (node.child[word.charAt(index) - 'a'] != null  &&
                    search(word, node.child[word.charAt(index) - 'a'], index+1));
        }else{
            for(TrieNode n : node.child){
                if (n != null && search (word, n, index+1)) return true;
            }
            return false;
        }

    }
}

class TrieNode{

    TrieNode[] child;
    boolean isWord;

    TrieNode(){
        this.child = new TrieNode[26];
        this.isWord = false;
    }
}