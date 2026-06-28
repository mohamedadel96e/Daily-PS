---
created: 2026-06-28T20:16
updated: 2026-06-28T20:56
Belongs To: "[[Daily-Questions]]"
---
# 1956. Maximum Element After Decreasing and Rearranging

| | |
|---|---|
| **URL** | [https://leetcode.com/problems/maximum-element-after-decreasing-and-rearranging/](https://leetcode.com/problems/maximum-element-after-decreasing-and-rearranging/) |
| **Difficulty** | 🟡 Medium |
| **Topics** | Array, Greedy, Sorting |
| **Status** | ⬜ Not Started |

---

## Problem

You are given an array of positive integers `arr`. Perform some operations (possibly none) on `arr` so that it satisfies these conditions:

  
  
- The value of the **first** element in `arr` must be `1`.  
- The absolute difference between any 2 adjacent elements must be **less than or equal to **`1`. In other words, `abs(arr[i] - arr[i - 1]) <= 1` for each `i` where `1 <= i < arr.length` (**0-indexed**). `abs(x)` is the absolute value of `x`. 
  There are 2 types of operations that you can perform any number of times:

  
  
- **Decrease** the value of any element of `arr` to a **smaller positive integer**.  
- **Rearrange** the elements of `arr` to be in any order. 
  Return _the **maximum** possible value of an element in _`arr`_ after performing the operations to satisfy the conditions_.

   

 **Example 1:**

  
```

**Input:** arr = [2,2,1,2,1]
**Output:** 2
**Explanation:** 
We can satisfy the conditions by rearranging arr so it becomes [1,2,2,2,1].
The largest element in arr is 2.

```
  **Example 2:**

  
```

**Input:** arr = [100,1,1000]
**Output:** 3
**Explanation:** 
One possible way to satisfy the conditions is by doing the following:
1. Rearrange arr so it becomes [1,100,1000].
2. Decrease the value of the second element to 2.
3. Decrease the value of the third element to 3.
Now arr = [1,2,3], which satisfies the conditions.
The largest element in arr is 3.

```
  **Example 3:**

  
```

**Input:** arr = [1,2,3,4,5]
**Output:** 5
**Explanation:** The array already satisfies the conditions, and the largest element is 5.

```
   

 **Constraints:**

  
  
- `1 <= arr.length <= 10^5`  
- `1 <= arr[i] <= 10^9`

---

## Approach

Let's consider the array:

![Screenshot 2026-06-28 071720.png](https://assets.leetcode.com/users/images/da914781-1e69-4d30-bcd0-031c0ea146ca_1782602255.0757964.png)

We can greedily select the smallest number in the array and set it to 1.

After we already pick the smallest, the next larger elements are guaranteed to be ≥prev, and similarly for the proceeding values.

![ndj.gif](https://assets.leetcode.com/users/images/55e00f78-e34a-4301-9267-0053a2a0593a_1782600014.2842612.gif)

---

This allows us to continue assigning values in increasing order, ensuring that the value of the next position can be matched as follows:

- if equal, we assign it directly;
- if greater than, it can be decreased to (prev+1).

This ensures:

∣arr[i]−arr[i−1]∣≤1​

---

Since we are allowed to rearrange the elements in any order:

- We can simply sort the array in increasing order to easily perform these operations.

![Screenshot 2026-06-28 073018.png](https://assets.leetcode.com/users/images/e05abf0a-3fc5-4e9d-90b8-40232da2777f_1782603245.3412511.png)

The maximum possible value is exactly the last element of the array.

---

## Sorting | In-place modification
```java
class Solution {
    public int maximumElementAfterDecrementingAndRearranging(int[] A) {
        Arrays.sort(A);
        int n = A.length;

        A[0] = 1;
        for (int i = 1; i < n; i++)
            A[i] = Math.min(A[i], A[i - 1] + 1);
        
        return A[n - 1];
    }
}
```
**Time Complexity:** O(nlog n)  
**Space Complexity:** O(log n) or O(n)

> This can also be implemented without modifying the array.

---

## Counting
```java
class Solution {
    private static final int[] freq = new int[100005];
    public int maximumElementAfterDecrementingAndRearranging(int[] A) {
        int n = A.length;
        for (int x : A)
            freq[Math.min(x, n)]++;

        int res = 1;
        for (int i = 2; i <= n; i++)
            res = Math.min(res + freq[i], i);

        for (int i = 0; i <= n; i++)
            freq[i] = 0;

        return res;
    }
}
```
Since final sequence cannot exceed length n, we clamp every value greater than n into n and count frequencies.

- Then, we simulate building the sequence from 1 to n, keeping the largest value we can reach.
    
- At each step, we add the available count for the current value and cap the result to ensure it does not exceed the current position.
    

**Time Complexity:** O(n)  
**Space Complexity:** O(n)