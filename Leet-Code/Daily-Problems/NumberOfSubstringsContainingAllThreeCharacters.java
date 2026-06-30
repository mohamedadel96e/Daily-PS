package org.example;

import java.nio.MappedByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * #1460 · Number of Substrings Containing All Three Characters
 * URL        : https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
 * Difficulty : Medium
 */
public class NumberOfSubstringsContainingAllThreeCharacters {

    class Solution {
        public int numberOfSubstrings(String s) {
            int[] freq = new int[3];
            int lastAIndex = -1;
            int lastBIndex = -1;
            int lastCIndex = -1;
            int res =0;
            for (int i = s.length() - 1; i >= 0; i--) {
                freq[s.charAt(i) - 'a']++;
                if (s.charAt(i) == 'a') {
                    lastAIndex = i;
                }
                if (s.charAt(i) == 'b') {
                    lastBIndex = i;
                }
                if (s.charAt(i) == 'c') {
                    lastCIndex = i;
                }
                if (lastAIndex != -1 && lastBIndex != -1 && lastCIndex != -1) {
                    int temp = Math.max(lastAIndex, lastBIndex);
                    res += s.length() - Math.max(lastCIndex, temp);
                }
            }
            return res;
        }
    }

    class AnotherSolution {
        public int numberOfSubstrings(String s) {
            char[] ch = s.toCharArray();
            int[] abc = new int[3];
            Arrays.fill(abc, -1);
            int count = 0, right = 0;
            while(right < ch.length){
                abc[ch[right] - 'a'] = right;
                int minIndex = Integer.MAX_VALUE;
                for(int i = 0; i < 3; i++){
                    minIndex = Math.min(minIndex, abc[i]);
                }
                count += (minIndex + 1);
                right++;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        NumberOfSubstringsContainingAllThreeCharacters sol = new NumberOfSubstringsContainingAllThreeCharacters();
        String test = "abcabc";
        sol.new Solution().numberOfSubstrings(test);
    }
}
