---
created: 2026-06-26T11:35
updated: 2026-06-26T16:23
Belongs To: "[[Daily-Questions]]"
---
# 4075. Count Subarrays With Majority Element II

| | |
|---|---|
| **URL** | [https://leetcode.com/problems/count-subarrays-with-majority-element-ii/](https://leetcode.com/problems/count-subarrays-with-majority-element-ii/) |
| **Difficulty** | 🔴 Hard |
| **Topics** | Array, Hash Table, Divide and Conquer, Segment Tree, Merge Sort, Prefix Sum |
| **Status** | ⬜ Not Started |

---

## Problem

You are given an integer array `nums` and an integer `target`.

  Return the number of **subarrays** of `nums` in which `target` is the **majority element**.

  The **majority element** of a subarray is the element that appears **strictly more than half** of the times in that subarray.

   

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

  
  
- `1 <= nums.length <= 10^​​​​​​​5`  
- `1 <= nums[i] <= 10^​​​​​​​9`  
- `1 <= target <= 10^9`

---

## Approach
### Intuition

When looking for a strict majority, the specific values of the non-target elements do not matter; all that matters is whether an element is the `target` or not.

My initial thought is to map the `target` elements to **1** and all other elements to **-1**. By doing this, a subarray has the `target` as a majority if and only if the sum of its elements is strictly greater than **0**.

To efficiently calculate the sums of any subarrays, we can use prefix sums. The problem then boils down to finding how many pairs of indices $(j, i)$ with $j \le i$ satisfy the condition $pre[i] - pre[j-1] > 0$, which simplifies to $pre[i] > pre[j-1]$. Instead of a brute-force nested loop to check all pairs, we can maintain a running count of valid previous prefix sums to achieve a linear time solution.

### Algorithm

1. **Transformation:** Iterate through `nums`. If `nums[i] == target`, change it to **1**. Otherwise, change it to **-1**.
    
2. **Prefix Sums:** Create an array `pre` where `pre[i]` holds the cumulative sum of the transformed array up to index $i$.
    
3. **Frequency Array Setup:** Initialize a `freq` array. Since prefix sums can range from $-n$ to $+n$, make its size $2n + 1$. Offset all index accesses by $+n$ to prevent negative index out-of-bounds errors. Set `freq[n] = 1` to account for the implicit prefix sum of **0** before the array starts.
    
4. **Dynamic Sliding Count:** Iterate through `pre`, keeping track of a `valid` variable (which counts how many previous prefix sums are strictly less than our current sum) and `lastSum` (the prefix sum from the previous iteration).
    
    - **If the sum goes up** (`pre[i] > lastSum`): The values that were equal to `lastSum` are now strictly smaller than our new sum. We add their frequency to `valid`.
        
    - **If the sum goes down** (`pre[i] < lastSum`): The values that are equal to our new `pre[i]` are no longer strictly smaller (they are now equal to our current sum). We subtract their frequency from `valid`.
        
5. **Accumulate & Update:** Add the `valid` count to the total `cnt`. Then, increment the frequency of the current `pre[i]` (with the $+n$ offset) and update `lastSum`.
    
6. Return `cnt`.
    

### Complexity

- **Time:** $\mathcal{O}(N)$
    
    We iterate through the array of length $N$ three times: once to transform, once to build prefix sums, and once to dynamically count valid subarrays. Every operation inside the loops takes constant $\mathcal{O}(1)$ time.
    
- **Space:** $\mathcal{O}(N)$
    
    We use an array `pre` of size $N$ and a `freq` array of size $2N + 1$.
    

### Solution

```java
public long countMajoritySubarrays(int[] nums, int target) {
    int n = nums.length;
    long cnt = 0;

    // Step 1: Transform array to +1 / -1
    for (int i = 0; i < n; i++) {
        if (nums[i] == target) nums[i] = 1;
        else nums[i] = -1;
    }
    
    // Step 2: Build prefix sum array
    int[] pre = new int[n];
    pre[0] = nums[0];
    for (int i = 1; i < n; i++) {
        pre[i] = pre[i - 1] + nums[i];
    }

    // Step 3: Initialize frequency array with +n offset
    int[] freq = new int[2 * n + 1];
    freq[n] = 1; // Represents the sum of 0 before index 0

    long valid = 0;
    int lastSum = 0;

    // Step 4 & 5: Slide and dynamically count valid previous sums
    for (int i = 0; i < n; i++) {
        if (pre[i] > lastSum) valid += freq[lastSum + n];
        else valid -= freq[pre[i] + n];

        cnt += valid;
        freq[pre[i] + n]++;
        lastSum = pre[i];
    }
    
    // Step 6: Return answer
    return cnt;
} 
```

### Example Walkthrough

- **Input:** `nums` = `[3, 2, 3, 3, 1]`, `target = 3`
    
- **Transformed:** `[1, -1, 1, 1, -1]`
    
- **Prefix Sums (`pre`):** `[1, 0, 1, 2, 1]`
    
- **Initial State:** `n = 5`, `freq[5] = 1` (Offset is $+5$), `valid = 0`, `lastSum = 0`, `cnt = 0`.
    

|**i**|**pre[i]**|**Condition**|**Math for valid**|**valid**|**cnt**|**Update freq (sum + 5)**|**New lastSum**|
|---|---|---|---|---|---|---|---|
|**0**|**1**|$1 > 0$ (UP)|Add `freq[0+5]` (1)|**1**|**1**|`freq[6]` becomes 1|1|
|**1**|**0**|$0 < 1$ (DOWN)|Sub `freq[0+5]` (1)|**0**|**1**|`freq[5]` becomes 2|0|
|**2**|**1**|$1 > 0$ (UP)|Add `freq[0+5]` (2)|**2**|**3**|`freq[6]` becomes 2|1|
|**3**|**2**|$2 > 1$ (UP)|Add `freq[1+5]` (2)|**4**|**7**|`freq[7]` becomes 1|2|
|**4**|**1**|$1 < 2$ (DOWN)|Sub `freq[1+5]` (2)|**2**|**9**|`freq[6]` becomes 3|1|

**Result:** There are exactly **9** subarrays where `3` is the majority.

### Notes

- **The "+N" Offset Detail:** Because `pre[i]` can fall below zero (if there are many non-target elements early on), attempting to write `freq[-1]` would crash the program. By adding $N$ (the maximum possible negative sum) to every sum when accessing `freq`, we map a sum of $-N$ to index **0**, a sum of **0** to index $N$, and a sum of $+N$ to index $2N$.
    
- **The $\mathcal{O}(1)$ Slide Detail:** The most confusing part is usually `valid += freq[lastSum + n]` and `valid -= freq[pre[i] + n]`. Because array elements are strictly $+1$ or $-1$, a prefix sum can only change by exactly **1**. We don't need to rescan the `freq` array to see how many numbers are smaller than `pre[i]`. We know exactly what shifted in or out of the "strictly smaller" boundary based purely on whether we stepped up or down.
    
- **`freq[n] = 1` Detail:** This is crucial. It acts as an anchor representing the "empty subarray" that exists right before index **0**, which has a sum of **0**. Without this, subarrays starting perfectly from index **0** would not be counted properly.