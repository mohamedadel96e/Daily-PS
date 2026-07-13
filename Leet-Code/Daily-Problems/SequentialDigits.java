package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * #1212 · Sequential Digits
 * URL        : https://leetcode.com/problems/sequential-digits/
 * Difficulty : Medium
 */
public class SequentialDigits {

    class Solution {
        public List<Integer> sequentialDigits(int low, int high) {
            List<Integer> sequentialDigits = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                getSequentialDigit(sequentialDigits, i);
            }
            Collections.sort(sequentialDigits);
            List<Integer> ans = new ArrayList<>();
            for (Integer sequentialDigit : sequentialDigits) {
                if (sequentialDigit >= low && sequentialDigit <= high) {
                    ans.add(sequentialDigit);
                }
            }
            return ans;
        }

        public void getSequentialDigit(List<Integer> sequentialDigits, int start) {
            int num = 0;
            while (start != 10) {
                num = num * 10 + start;
                start++;
                sequentialDigits.add(num);
            }
        }
    }

    class AnotherSolution {
        static final int[] q = new int[45];

        static {
            int n = 0;

            for (int i = 1; i < 10; i++)
                q[n++] = i;

            for (int i = 0; i < n; i++) {
                int d = q[i] % 10;

                if (d < 9)
                    q[n++] = q[i] * 10 + d + 1;
            }
        }

        public List<Integer> sequentialDigits(int low, int high) {
            List<Integer> res = new ArrayList<>();

            for (int x : q)
                if (x >= low && x <= high)
                    res.add(x);

            return res;
        }
    }

    public static void main(String[] args) {
        SequentialDigits sol = new SequentialDigits();
        // TODO: add test cases
    }
}
