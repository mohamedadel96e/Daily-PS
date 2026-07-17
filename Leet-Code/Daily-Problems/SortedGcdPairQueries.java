package org.example;

import java.util.Arrays;

/**
 * #3583 · Sorted GCD Pair Queries
 * URL        : https://leetcode.com/problems/sorted-gcd-pair-queries/
 * Difficulty : Hard
 */
public class SortedGcdPairQueries {

    class Solution {
        public int[] gcdValues(int[] nums, long[] queries) {
            int max = Arrays.stream(nums).max().getAsInt();

            int[] freq = new int[max + 1];
            long[] GCD = new long[max + 1];

            for (int n : nums) freq[n]++;

            for (int i = max; i > 0; i--) {
                long sum = 0, extra = 0;
                for (int j = i; j <= max; j += i) {
                    sum += freq[j];
                    extra += GCD[j];
                }
                GCD[i] = sum * (sum - 1) / 2 - extra;
            }

            Arrays.parallelPrefix(GCD, Long::sum);
            int n = queries.length;

            int[] res = new int[n];
            for (int i =0; i < n; i++) {
                long q = queries[i];
                int l = 0, r = max + 1;
                while (l < r) {
                    int mid = l + (r - l) / 2;
                    if (GCD[mid] <= q) l = mid + 1;
                    else r = mid;
                }
                res[i] = l;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        SortedGcdPairQueries sol = new SortedGcdPairQueries();
        // TODO: add test cases
    }
}
