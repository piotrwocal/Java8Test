package com.pwocal.trie;


public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert( String word ) {
        TrieNode current = root;
        for( int i = 0; i < word.length(); i++ ) {
            char c = word.charAt( i );
            int index = c - 'a' ;
            if( current.arr[ index ] == null ) {
                TrieNode temp = new TrieNode();
                current.arr[ index ] = temp;
                current = temp;
            } else {
                current = current.arr[ index ];
            }
        }
        current.isEnd = true;
    }

    // Returns if the word is in the trie.
    public boolean search( String word ) {
        TrieNode current = searchNode( word );
        if( current == null ) {
            return false;
        }
        return current.isEnd;
    }

    public TrieNode searchNode( String s ) {
        TrieNode current = root;
        for( int i=0; i < s.length(); i++ ) {
            char c = s.charAt( i );
            int index = c - 'a';
            if( current.arr[ index ] == null ){
                return null;
            }
            current = current.arr[ index ];
        }
        return  current == root ? null : current;
    }

    private static class TrieNode {
        TrieNode[] arr;
        boolean isEnd;
        public TrieNode() {
            this.arr = new TrieNode[ 10 ];
        }
    }

}
