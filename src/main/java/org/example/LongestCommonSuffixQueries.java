package org.example;

public class LongestCommonSuffixQueries {



    public static void main(String[] args) {
        String[] wordsContainer = {"abcd", "bcd", "xbcd"};
        String[] wordsQuery = {"cd", "bcd", "xyz"};

        System.out.println(stringIndices(wordsContainer, wordsQuery));
    }

    // Trie node for reversed-word suffix matching.
    // bestLen/bestIdx track the shortest container word (and smallest index on ties)
    // reachable at this node; this enables O(1) answer once the walk stops.
    static class TrieNode {
        TrieNode[] children =  new TrieNode[26];
        int bestLen = Integer.MAX_VALUE;
        int bestIdx = Integer.MAX_VALUE;
    }

    public static int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        // Build a trie over reversed container words.
        // Each prefix in this reversed trie corresponds to a suffix in the original word.
        TrieNode root = new TrieNode();

        // Insert every container word into the reversed trie while maintaining
        // the best (shortest, then smallest index) word that passes through each node.
        for (int i = 0; i < wordsContainer.length; i++) {
            String word = wordsContainer[i];
            int len = word.length();
            TrieNode curr = root;

            // Update the root for the empty suffix match.
            if (len < curr.bestLen || (len == curr.bestLen && i < curr.bestIdx)) {
                curr.bestLen = len;
                curr.bestIdx = i;
            }

            // Walk characters from the end to the start to represent suffixes.
            for (int j = len - 1; j >= 0; j--) {
                int charIdx = word.charAt(j) - 'a';

                if (curr.children[charIdx] == null) {
                    curr.children[charIdx] = new TrieNode();
                }

                curr = curr.children[charIdx];

                // Maintain best candidate for this suffix path.
                if (len < curr.bestLen || (len == curr.bestLen && i < curr.bestIdx)) {
                    curr.bestLen = len;
                    curr.bestIdx = i;
                }
            }
        }

        int[] ans = new int[wordsQuery.length];

        // For each query, follow its reversed characters as far as possible.
        // The deepest reachable node holds the best index for the longest matched suffix.
        for (int i = 0; i < wordsQuery.length; i++) {
            String query = wordsQuery[i];
            int len = query.length();
            TrieNode curr = root;

            for (int j = len - 1; j >= 0; j--) {
                int charIdx = query.charAt(j) - 'a';
                if (curr.children[charIdx] == null) {
                    break;
                }
                curr = curr.children[charIdx];
            }
            ans[i] = curr.bestIdx;
        }
        return ans;
    }
}
