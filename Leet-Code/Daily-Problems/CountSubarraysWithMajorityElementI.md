---
created: 1970-01-01T02:00
updated: 2026-06-25T13:43
---
# 4074. Count Subarrays With Majority Element I

| | |
|---|---|
| **URL** | [https://leetcode.com/problems/count-subarrays-with-majority-element-i/](https://leetcode.com/problems/count-subarrays-with-majority-element-i/) |
| **Difficulty** | 🟡 Medium |
| **Topics** | Array, Hash Table, Divide and Conquer, Segment Tree, Merge Sort, Counting, Prefix Sum |
| **Status** | ⬜ Not Started |

---

## Problem

You are given an integer array `nums` and an integer `target`.

  Return the number of **subarrays** of `nums` in which `target` is the **majority element**.

  The **majority element** of a subarray is the element that appears **strictly** **more than half** of the times in that subarray.

   

 **Example 1:**

   **Input:** nums = [1,2,2,3], target = 2

  **Output:** 5

  **Explanation:**

  Valid subarrays with `target = 2` as the majority element:

  
  
- `nums[1..1] = [2]`  
- `nums[2..2] = [2]`  
- `nums[1..2] = [2,2]`  
- `nums[0..2] = [1,2,2]`  
- `nums[1..3] = [2,2,3]` 
  So there are 5 such subarrays.

   **Example 2:**

   **Input:** nums = [1,1,1,1], target = 1

  **Output:** 10

  **Explanation: **

  **​​​​​​​**All 10 subarrays have 1 as the majority element.

   **Example 3:**

   **Input:** nums = [1,2,3], target = 4

  **Output:** 0

  **Explanation:**

  `target = 4` does not appear in `nums` at all. Therefore, there cannot be any subarray where 4 is the majority element. Hence the answer is 0.

    

 **Constraints:**

  
  
- `1 <= nums.length <= 1000`  
- `1 <= nums[i] <= 10^​​​​​​​9`  
- `1 <= target <= 10^9`

---

## Approach

# Method 1️⃣: Brute Force / Direct Majority Check

---

## Idea

The most straightforward way is to generate every possible subarray and directly check whether `target` is the majority element.

For each subarray, we count how many times `target` appears. If its frequency is strictly greater than half of the subarray length, we count that subarray as valid.

This approach follows the problem statement exactly, making it easy to understand, but it repeatedly performs the same calculations for overlapping subarrays.

---

## Approach

### Step 1: Understand the starting condition

- We need to count subarrays where `target` is the majority element.
- A majority element appears more than half of the subarray length.

---

### Step 2: Process each subarray

For every starting index `l`:

- Initialize `targetCount = 0`.
- Extend the subarray by moving `r` from `l` to `n - 1`.

For every new element:

- If `nums[r] == target`, increment `targetCount`.
- Compute the current subarray length.

---

### Step 3: Track the answer

For the current subarray:

```text
len = r - l + 1
```

Check:

```text
targetCount > len / 2
```

If true:

- Increase the answer.

---

### Step 4: Finish computation

- Repeat for all possible starting positions.
- Return the total count.

---

## Why it works

We explicitly examine every possible subarray and verify the majority condition directly.

Since every subarray is checked exactly once, every valid subarray is counted and every invalid subarray is ignored.

> target is majority ⇔ targetCount > subarrayLength / 2

---

## Solution

```java
class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        int ans = 0;

        for (int l = 0; l < n; l++) {
            int targetCount = 0;

            for (int r = l; r < n; r++) {
                if (nums[r] == target) {
                    targetCount++;
                }

                int len = r - l + 1;

                if (targetCount > len / 2) {
                    ans++;
                }
            }
        }

        return ans;
    }
}
```

---

## Complexity Analysis

### ⏱ Time Complexity: **O(n²)**

- There are O(n²) subarrays.
- Each subarray is processed in O(1) while extending the right boundary.

---

### 💾 Space Complexity: **O(1)**

- Only a few variables are used.

---

## Key Insight

We repeatedly check the majority condition for overlapping subarrays.

Instead of working with frequencies directly, we can convert the problem into a subarray-sum problem.

---

# ✔ Method 2️⃣: Prefix Sum Enumeration

---

## Idea

Instead of checking majority directly, we transform the array:

```text
target -> +1
others -> -1
```

Now for any subarray:

```text
sum = targetCount - nonTargetCount
```

For `target` to be the majority:

```text
targetCount > nonTargetCount
```

which means:

```text
sum > 0
```

So the problem becomes counting subarrays whose transformed sum is positive.

---

## Approach

### Step 1: Identify the optimization

- Majority checking is replaced by sum checking.
- Prefix sums allow us to compute subarray sums in O(1).

---

### Step 2: Build the optimized structure

Create a prefix sum array:

```text
pref[i]
```

where:

```text
pref[i + 1] = pref[i] + (nums[i] == target ? 1 : -1)
```

---

### Step 3: Update the answer

For every subarray:

```text
sum = pref[r + 1] - pref[l]
```

If:

```text
sum > 0
```

then the subarray is valid.

---

### Step 4: Return the result

- Count all positive-sum subarrays.
- Return the answer.

---

## Why it works

After transformation:

```text
sum = targetCount - nonTargetCount
```

A majority element requires:

```text
targetCount > nonTargetCount
```

Therefore:

```text
sum > 0
```

So checking majority becomes checking whether the transformed subarray sum is positive.

> target majority ⇔ transformed subarray sum > 0

---

## Solution

```java
class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;

        int[] pref = new int[n + 1];

        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + (nums[i] == target ? 1 : -1);
        }

        int ans = 0;

        for (int l = 0; l < n; l++) {
            for (int r = l; r < n; r++) {
                if (pref[r + 1] - pref[l] > 0) {
                    ans++;
                }
            }
        }

        return ans;
    }
}
```

---

## Complexity Analysis

### ⏱ Time Complexity: **O(n²)**

- O(n) to build prefix sums.
- O(n²) to enumerate all subarrays.

---

### 💾 Space Complexity: **O(n)**

- Prefix sum array.

---

## Key Insight

We removed repeated frequency calculations.

However, we still check all O(n²) subarrays. The remaining challenge is counting valid subarrays without enumerating every pair.

---

# ✔ Method 3️⃣: Optimal — Prefix Sum Frequency

---

## Idea

From Method 2:

```text
pref[r + 1] - pref[l] > 0
```

which can be rewritten as:

```text
pref[l] < pref[r + 1]
```

Now the problem becomes:

> Count how many previous prefix sums are smaller than the current prefix sum.

A typical solution uses a Fenwick Tree and runs in O(n log n).

However, after transformation:

```text
target -> +1
others -> -1
```

the prefix sum changes by only:

```text
+1 or -1
```

Therefore, prefix sums are always within:

```text
[-n, n]
```

Since the range is bounded, we can store frequencies directly in an array and update the answer in O(1) time.

---

## Approach

### Step 1: Initialize the required state

Create:

```text
pre[2 * n + 1]
```

to store prefix-sum frequencies.

Use an offset of `n`:

```text
prefix sum 0 -> index n
```

Initialize:

```text
pre[n] = 1
```

because prefix sum `0` exists before processing any element.

---

### Step 2: Process the input once

Maintain:

```text
cnt
```

which represents the current prefix sum position.

For every element:

- Move right if it equals `target`.
- Move left otherwise.

---

### Step 3: Keep track of the answer

Maintain:

```text
presum
```

which stores the number of valid prefix pairs ending at the current position.

If we move right:

```text
presum += pre[cnt]
```

If we move left:

```text
presum -= pre[cnt]
```

Then add:

```text
ans += presum
```

---

### Step 4: Finish

- Process all elements once.
- Return the final answer.

---

## Why it works

A valid subarray satisfies:

```text
pref[r + 1] - pref[l] > 0
```

which is equivalent to:

```text
pref[l] < pref[r + 1]
```

So we only need to count smaller previous prefix sums.

Because prefix sums move only one step at a time (`+1` or `-1`), we can maintain this count incrementally without using any advanced data structure.

> Count valid prefix-sum pairs while updating the prefix sum one step at a time.

---

## Solution

```java
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
```

### Complexity Analysis

#### ⏱ Time Complexity: **O(n)**

- Single pass through the array.
- Every update and query is O(1).

---

#### 💾 Space Complexity: **O(n)**

- Frequency array of size `2n + 1`.

---

## Key Insight

The transformed prefix sum can only move by one step at a time.

Instead of using a Fenwick Tree or Segment Tree, we exploit the bounded prefix-sum range and maintain frequencies directly.

This reduces:

```text
O(n log n)
```

to:

```text
O(n)
```

making it the most efficient solution for this problem.