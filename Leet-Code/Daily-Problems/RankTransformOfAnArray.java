package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * #1256 · Rank Transform of an Array
 * URL        : https://leetcode.com/problems/rank-transform-of-an-array/
 * Difficulty : Easy
 */
public class RankTransformOfAnArray {

    class Solution {
        public int[] arrayRankTransform(int[] arr) {
            int[] temp = arr.clone();
            Arrays.sort(temp);

            int m = 0;
            for (int x: temp) {
                if (m == 0 || x != temp[m - 1]) {
                    temp[m++] = x;
                }
            }
            int[] unique = Arrays.copyOf(temp, m);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = Arrays.binarySearch(unique, arr[i]) + 1;
            }
            return arr;
        }
    }

    public static void main(String[] args) {
        RankTransformOfAnArray sol = new RankTransformOfAnArray();
        // TODO: add test cases
    }
}
