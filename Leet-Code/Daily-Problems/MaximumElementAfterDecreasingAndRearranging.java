package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * #1956 · Maximum Element After Decreasing and Rearranging
 * URL        : https://leetcode.com/problems/maximum-element-after-decreasing-and-rearranging/
 * Difficulty : Medium
 */
public class MaximumElementAfterDecreasingAndRearranging {

    class Solution {
        private static final int MAXN = (int) 1e5;
        private static int[] freq = new int[MAXN + 1];
        public int maximumElementAfterDecrementingAndRearranging(int[] A) {
            int n = A.length;
            for (int x : A)
                freq[Math.min(x, n)]++;

            int res = 1;
            for (int i = 2; i <= n; i++)
                res = Math.min(res + freq[i], i);

            for (int i = 0; i <= n; i++)
                freq[i] = 0;

            return res;
        }
    }

    public static void main(String[] args) {
        MaximumElementAfterDecreasingAndRearranging sol = new MaximumElementAfterDecreasingAndRearranging();
        // TODO: add test cases
    }
}
