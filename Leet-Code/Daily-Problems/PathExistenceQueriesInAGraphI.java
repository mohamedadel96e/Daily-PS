package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * #3838 · Path Existence Queries in a Graph I
 * URL        : https://leetcode.com/problems/path-existence-queries-in-a-graph-i/
 * Difficulty : Medium
 */
public class PathExistenceQueriesInAGraphI {

    class Solution {

        public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
            int[] minInPath = new int[n];
            minInPath[0] = nums[0];
            for (int i = 1; i < n; i++) {
                if (nums[i] - nums[i - 1] <= maxDiff) {
                    minInPath[i] = minInPath[i - 1];
                } else {
                    minInPath[i] = nums[i];
                }
            }
            boolean[] ans = new boolean[queries.length];
            for (int i = 0; i < queries.length; i++) {
                ans[i] = minInPath[queries[i][0]] == minInPath[queries[i][1]];
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        PathExistenceQueriesInAGraphI sol = new PathExistenceQueriesInAGraphI();
        // TODO: add test cases
        // n = 4, nums = [2,5,6,8], maxDiff = 2, queries = [[0,1],[0,2],[1,3],[2,3]]
        System.out.println(Arrays.toString(sol.new Solution().pathExistenceQueries(4, new int[]{2, 5, 6, 8}, 2, new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}})));
    }
}
