package org.example;

/**
 * #4074 · Count Subarrays With Majority Element I
 * URL        : https://leetcode.com/problems/count-subarrays-with-majority-element-i/
 * Difficulty : Medium
 */
public class CountSubarraysWithMajorityElementI {

    class Solution {
        public int countMajoritySubarrays(int[] nums, int target) {
            int n = nums.length;

            int[] pre = new int[2 * n + 1];

            pre[n] = 1;

            int cnt = n;
            int presum = 0;
            int ans = 0;

            for (int x : nums) {
                if (x == target) {
                    presum += pre[cnt];

                    cnt++;
                    pre[cnt]++;
                } else {
                    cnt--;

                    presum -= pre[cnt];
                    pre[cnt]++;
                }

                ans += presum;
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        CountSubarraysWithMajorityElementI sol = new CountSubarraysWithMajorityElementI();
        int[] test = {1,2,2,3};

        System.out.println(sol.new Solution().countMajoritySubarrays(test, 2));
    }
}
