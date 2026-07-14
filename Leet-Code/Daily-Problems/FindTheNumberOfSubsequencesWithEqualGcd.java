package org.example;

import java.util.Arrays;

/**
 * #3608 · Find the Number of Subsequences With Equal GCD
 * URL        : https://leetcode.com/problems/find-the-number-of-subsequences-with-equal-gcd/
 * Difficulty : Hard
 */
public class FindTheNumberOfSubsequencesWithEqualGcd {

    class Solution {
        private static final int MOD = 1_000_000_007;

        public int subsequencePairCount(int[] nums) {
            // cur: Combinations before processing the current number
            // nxt: Combinations after processing the current number
            int[][] cur = new int[201][201];
            int[][] nxt = new int[201][201];

            // 0 means the sequence is currently empty.
            cur[0][0] = 1;

            for (int x : nums) {
                // Clear the slate for the next round of calculations
                for (int i = 0; i <= 200; i++) {
                    Arrays.fill(nxt[i], 0);
                }

                // Look at every possible state we currently have
                for (int g1 = 0; g1 <= 200; g1++) {
                    for (int g2 = 0; g2 <= 200; g2++) {

                        // If we haven't reached this combination yet, skip it
                        if (cur[g1][g2] == 0) continue;

                        long ways = cur[g1][g2];

                        // Choice 1: Throw 'x' in the trash
                        nxt[g1][g2] = (int) ((nxt[g1][g2] + ways) % MOD);

                        // Choice 2: Put 'x' in Sequence 1
                        // If Seq 1 is empty (g1 == 0), its new GCD is just 'x'
                        int ng1 = (g1 == 0) ? x : gcd(g1, x);
                        nxt[ng1][g2] = (int) ((nxt[ng1][g2] + ways) % MOD);

                        // Choice 3: Put 'x' in Sequence 2
                        // If Seq 2 is empty (g2 == 0), its new GCD is just 'x'
                        int ng2 = (g2 == 0) ? x : gcd(g2, x);
                        nxt[g1][ng2] = (int) ((nxt[g1][ng2] + ways) % MOD);
                    }
                }

                // Swap the arrays. 'cur' now holds the results of this round.
                int[][] temp = cur;
                cur = nxt;
                nxt = temp;
            }

            long ans = 0;
            // Sum up all combinations where Seq 1 and Seq 2 have the exact same GCD.
            // We start at 1 to ignore the [0][0] state (which means both are empty).
            for (int g = 1; g <= 200; g++) {
                ans = (ans + cur[g][g]) % MOD;
            }

            return (int) ans;
        }

        // Standard Euclidean algorithm to find the Greatest Common Divisor
        private int gcd(int a, int b) {
            while (b != 0) {
                int t = a % b;
                a = b;
                b = t;
            }
            return a;
        }
    }

    public static void main(String[] args) {
        FindTheNumberOfSubsequencesWithEqualGcd sol = new FindTheNumberOfSubsequencesWithEqualGcd();
        // TODO: add test cases
    }
}
