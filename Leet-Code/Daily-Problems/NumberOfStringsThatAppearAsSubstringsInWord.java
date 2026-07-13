package org.example;

/**
 * #2099 · Number of Strings That Appear as Substrings in Word
 * URL        : https://leetcode.com/problems/number-of-strings-that-appear-as-substrings-in-word/
 * Difficulty : Easy
 */
public class NumberOfStringsThatAppearAsSubstringsInWord {

    class Solution {
        public int numOfStrings(String[] patterns, String word) {
            int res = 0;
            for (String s : patterns) {
                if (word.contains(s)) {
                    res++;
                }
            }

            return res;
        }
    }

    public static void main(String[] args) {
        NumberOfStringsThatAppearAsSubstringsInWord sol = new NumberOfStringsThatAppearAsSubstringsInWord();
        // TODO: add test cases
    }
}
