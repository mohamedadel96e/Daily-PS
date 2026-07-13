package org.example;

/**
 * #4136 · Concatenate Non-Zero Digits and Multiply by Sum II
 * URL        : https://leetcode.com/problems/concatenate-non-zero-digits-and-multiply-by-sum-ii/
 * Difficulty : Medium
 */
public class ConcatenateNonZeroDigitsAndMultiplyBySumIi {

    class Solution {
        private static final long MOD = 1_000_000_007L;
        public int[] sumAndMultiply(String s, int[][] queries) {
            int n = s.length();

            long[] value = new long[n + 1];
            int[] digitSum = new int[n + 1];
            int[] count = new int[n + 1];
            long[] pow10 = new long[n + 1];

            pow10[0] = 1;
            for (int i = 1; i <= n; i++) {
                pow10[i] = (pow10[i - 1] * 10) % MOD;
            }

            for (int i = 0; i < n; i++) {
                int d = s.charAt(i) - '0';

                digitSum[i + 1] = digitSum[i];
                count[i + 1] = count[i];
                value[i + 1] = value[i];

                if (d != 0) {
                    value[i + 1] = (value[i] * 10 + d) % MOD;
                    digitSum[i + 1] += d;
                    count[i + 1]++;
                }
            }

            int[] ans = new int[queries.length];

            for (int i = 0; i < queries.length; i++) {
                int l = queries[i][0];
                int r = queries[i][1];

                int digitsInside = count[r + 1] - count[l];

                long x;

                if (digitsInside == 0) {
                    x = 0;
                } else {
                    x = value[r + 1]
                            - (value[l] * pow10[digitsInside]) % MOD;

                    if (x < 0) x += MOD;
                }

                long sum = digitSum[r + 1] - digitSum[l];

                ans[i] = (int) ((x * sum) % MOD);
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ConcatenateNonZeroDigitsAndMultiplyBySumIi sol = new ConcatenateNonZeroDigitsAndMultiplyBySumIi();
        // TODO: add test cases
    }
}
