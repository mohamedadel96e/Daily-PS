package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * #3299 · Find the Maximum Number of Elements in Subset
 * URL        : https://leetcode.com/problems/find-the-maximum-number-of-elements-in-subset/
 * Difficulty : Medium
 */
public class FindTheMaximumNumberOfElementsInSubset {

    class Solution {
        private static final int maxBase = 31622;

        public int maximumLength(int[] nums) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int n: nums) map.put(n, map.getOrDefault(n, 0) + 1);

            int one = map.getOrDefault(1, 0);
            int res = (one - 1) | 1; // take the odd freq of the number 1
            map.remove(1);

            for (int freq: map.keySet()) {
                int sq = (int) Math.sqrt(freq);
                if (sq * sq == freq && map.getOrDefault(sq, 0) > 1) {
                    continue;
                }
                int n = 0, x = freq;

                while (x < maxBase && map.containsKey(x) && map.get(x) > 1) {
                    n += 2;
                    x *= x;
                }
                res = Math.max(res, n + (map.containsKey(x) ? 1 : -1));
            }

            return res;
        }
    }

    public static void main(String[] args) {
        FindTheMaximumNumberOfElementsInSubset sol = new FindTheMaximumNumberOfElementsInSubset();
        // TODO: add test cases
    }
}
