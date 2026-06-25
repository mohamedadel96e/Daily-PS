package org.example;

/**
 * #1833 · Find the Highest Altitude
 * URL        : https://leetcode.com/problems/find-the-highest-altitude/
 * Difficulty : Easy
 */
public class FindTheHighestAltitude {

    class Solution {
        public int largestAltitude(int[] gain) {
            int highest = 0;
            int cum = 0;
            for (int j : gain) {
                cum = cum + j;
                highest = Math.max(highest, cum);
            }

            return highest;
        }
    }

    public static void main(String[] args) {
        FindTheHighestAltitude sol = new FindTheHighestAltitude();
        // TODO: add test cases
    }
}
