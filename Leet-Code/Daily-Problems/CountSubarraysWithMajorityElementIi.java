package org.example;

/**
 * #4075 · Count Subarrays With Majority Element II
 * URL        : https://leetcode.com/problems/count-subarrays-with-majority-element-ii/
 * Difficulty : Hard
 */
public class CountSubarraysWithMajorityElementIi {

    class Solution {
        public long countMajoritySubarrays(int[] nums, int target) {
            int n = nums.length;
            long cnt = 0;

            for (int i = 0; i < n; i++) {
                if (nums[i] == target) nums[i] = 1;
                else nums[i] = -1;
            }
            int[] pre = new int[n];
            pre[0] = nums[0];

            for (int i = 1; i < n; i++)
                pre[i] = pre[i - 1] + nums[i];

            int[] freq = new int[2 * n + 1];
            freq[n] = 1;

            long valid = 0;
            int lastSum = 0;

            for (int i = 0; i < n; i++) {
                if (pre[i] > lastSum) valid += freq[lastSum + n];
                else valid -= freq[pre[i] + n];

                cnt += valid;
                freq[pre[i] + n]++;
                lastSum = pre[i];
            }
            return cnt;
        }
    }

    public static void main(String[] args) {
        CountSubarraysWithMajorityElementIi sol = new CountSubarraysWithMajorityElementIi();
        int[] test = {5, 2, 5, 1, 5, 3, 5, 5};
        sol.new Solution().countMajoritySubarrays(test, 5);
    }
}
