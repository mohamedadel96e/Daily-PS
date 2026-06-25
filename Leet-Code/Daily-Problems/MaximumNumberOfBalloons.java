package org.example;

/**
 * #1297 · Maximum Number of Balloons
 * URL        : https://leetcode.com/problems/maximum-number-of-balloons/
 * Difficulty : Easy
 */
public class MaximumNumberOfBalloons {

    class Solution {
        public int maxNumberOfBalloons(String text) {
            int[] freq = new int[26];

            for (int i = 0; i < text.length(); i++) {
                freq[text.charAt(i) - 'a']++;
            }

            return Math.min(freq[0],Math.min(freq[1], Math.min(freq['l' - 'a'] / 2, Math.min(freq['o' - 'a'] / 2, freq['n' - 'a']))));
        }
    }

    public static void main(String[] args) {
        MaximumNumberOfBalloons sol = new MaximumNumberOfBalloons();
        // TODO: add test cases
    }
}
