---
created: 2026-07-17T12:16
updated: 2026-07-17T13:03
---
# 3583. Sorted GCD Pair Queries

|                |                                                                                                                  |
| -------------- | ---------------------------------------------------------------------------------------------------------------- |
| **URL**        | [https://leetcode.com/problems/sorted-gcd-pair-queries/](https://leetcode.com/problems/sorted-gcd-pair-queries/) |
| **Difficulty** | 🔴 Hard                                                                                                          |
| **Topics**     | Array, Hash Table, Math, Binary Search, Combinatorics, Counting, Number Theory, Prefix Sum                       |


---

## Problem

You are given an integer array `nums` of length `n` and an integer array `queries`.

  Let `gcdPairs` denote an array obtained by calculating the GCD of all possible pairs `(nums[i], nums[j])`, where `0 <= i < j < n`, and then sorting these values in **ascending** order.

  For each query `queries[i]`, you need to find the element at index `queries[i]` in `gcdPairs`.

  Return an integer array `answer`, where `answer[i]` is the value at `gcdPairs[queries[i]]` for each query.

  The term `gcd(a, b)` denotes the **greatest common divisor** of `a` and `b`.

   

 **Example 1:**

   **Input:** nums = [2,3,4], queries = [0,2,2]

  **Output:** [1,2,2]

  **Explanation:**

  `gcdPairs = [gcd(nums[0], nums[1]), gcd(nums[0], nums[2]), gcd(nums[1], nums[2])] = [1, 2, 1]`.

  After sorting in ascending order, `gcdPairs = [1, 1, 2]`.

  So, the answer is `[gcdPairs[queries[0]], gcdPairs[queries[1]], gcdPairs[queries[2]]] = [1, 2, 2]`.

   **Example 2:**

   **Input:** nums = [4,4,2,1], queries = [5,3,1,0]

  **Output:** [4,2,1,1]

  **Explanation:**

  `gcdPairs` sorted in ascending order is `[1, 1, 1, 2, 2, 4]`.

   **Example 3:**

   **Input:** nums = [2,2], queries = [0,0]

  **Output:** [2,2]

  **Explanation:**

  `gcdPairs = [2]`.

    

 **Constraints:**

  
  
- `2 <= n == nums.length <= 10^5`  
- `1 <= nums[i] <= 5 * 10^4`  
- `1 <= queries.length <= 10^5`  
- `0 <= queries[i] < n * (n - 1) / 2`

---

## Approach

### Intuition

Generating every possible pair from an array of size $10^5$ means calculating and sorting $\approx 5 \times 10^9$ combinations. This will instantly trigger a Time Limit Exceeded (TLE) error or crash the memory. Instead of asking, "What is the GCD of each pair?", we must reverse the question: **"How many pairs have a GCD of exactly $x$?"**

To do this efficiently, we use a counting trick based on multiples. If we want to find all pairs with a GCD of exactly $2$, we first count all pairs of even numbers (multiples of $2$). However, a pair of even numbers (like $4$ and $8$) actually has a GCD of $4$, not $2$.

The fix is brilliant but simple: **Work backwards from the largest number.** If we calculate the exact pair counts for larger numbers first ($4, 6, 8 \dots$), we can count all pairs of even numbers and simply subtract the exact pair counts of those larger multiples. Because we move backwards, we always know the true counts of larger multiples before we need to subtract them.

Once we know exactly how many pairs exist for every possible GCD, we can turn that into a running total (prefix sum). These running totals act as "buckets," allowing us to answer any query instantly using binary search.

### Algorithm

1. **Frequency Array:** Find the maximum element $M$ in `nums`. Create an array `freq` to count how many times each number appears in the input. We no longer care about the length or order of the original array.
    
2. **Backwards Sieve (The Core Logic):** Loop `i` backwards from $M$ down to 1.
    
    - Find how many numbers in the array are multiples of `i` ($i, 2i, 3i \dots$). Sum their frequencies to get a total `sum`.
        
    - Calculate the maximum possible pairs you can form from these multiples using the standard combination formula: `sum * (sum - 1) / 2`.
        
    - Because these pairs might have a GCD that is a _larger_ multiple of `i` (e.g., a pair of 2s might actually be 4s), subtract the exact pair counts of those larger multiples (which we stored previously in `GCD[j]`).
        
    - Store this clean, exact pair count in `GCD[i]`.
        
3. **Prefix Sums:** Use `Arrays.parallelPrefix` to transform the `GCD` array into a cumulative sum. Now, `GCD[i]` represents the total number of pairs that have a GCD of $i$ _or less_.
    
4. **Binary Search Queries:** For each query, we are looking for the element at index `q` in a hypothetically sorted array. Using our prefix sum "buckets", we perform an upper-bound binary search to find the smallest bucket index `l` where the cumulative count is strictly greater than `q`.
    

### Complexity

- **Time:** $\mathcal{O}(N + M \log M + Q \log M)$
    
    $N$ is the length of `nums`, $M$ is the maximum value in `nums`, and $Q$ is the number of queries. Populating the frequency array takes $\mathcal{O}(N)$. The nested loop jumps by multiples ($M/1 + M/2 + M/3 \dots$), forming a harmonic series that mathematically bounds to $\mathcal{O}(M \log M)$. The binary search takes $\mathcal{O}(Q \log M)$.
    
- **Space:** $\mathcal{O}(M)$
    
    We allocate space for the `freq` and `GCD` arrays based strictly on the maximum integer value in `nums`, using no extra space for the pairs themselves.
    

### Solution

```java
class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        // Step 1: Find max value and build frequency array
        int max = Arrays.stream(nums).max().getAsInt();
        int[] freq = new int[max + 1];
        long[] GCD = new long[max + 1];

        for (int n : nums) freq[n]++;

        // Step 2: Backwards sieve to find EXACT counts of each GCD
        for (int i = max; i > 0; i--) {
            long sum = 0, extra = 0;
            
            // Jump by multiples of i
            for (int j = i; j <= max; j += i) {
                sum += freq[j];
                extra += GCD[j]; // Pairs that belong to larger multiples
            }
            
            // (Total pairs of multiples) - (Pairs of larger GCDs)
            GCD[i] = sum * (sum - 1) / 2 - extra;
        }
        
        // Step 3: Convert exact counts into a running total (prefix sum)
        Arrays.parallelPrefix(GCD, Long::sum);
        
        // Step 4: Binary search each query against the running totals
        int n = queries.length;
        int[] res = new int[n];
        
        for (int i = 0; i < n; i++) {
            long q = queries[i];
            int l = 0, r = max + 1;
            
            // Upper-bound binary search
            while (l < r) {
                int mid = l + (r - l) / 2;
                
                // Find the first bucket where cumulative count surpasses q
                if (GCD[mid] <= q) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            res[i] = l;
        }
        
        return res;
    }
}
```

### Example Walkthrough

**Input:** `nums = [2, 3, 4]`, `queries = [0, 2, 2]`

_The maximum value is 4. Frequencies: `freq[2]=1`, `freq[3]=1`, `freq[4]=1`._

**Phase 1: Backwards Sieve**

- **`i = 4`:** Multiples = `{4}`. `sum` = 1. Pairs = $(1 \times 0) / 2 = 0$. Exact `GCD[4] = 0`.
    
- **`i = 3`:** Multiples = `{3}`. `sum` = 1. Pairs = 0. Exact `GCD[3] = 0`.
    
- **`i = 2`:** Multiples = `{2, 4}`. `sum` = 2. Pairs = $(2 \times 1) / 2 = 1$. We subtract `extra` (`GCD[4]`, which is 0). Exact `GCD[2] = 1`.
    
- **`i = 1`:** Multiples = `{1, 2, 3, 4}`. `sum` = 3. Pairs = $(3 \times 2) / 2 = 3$. We subtract `extra` (`GCD[2] + GCD[3] + GCD[4] = 1`). Exact `GCD[1] = 2`.
    

**Phase 2: Prefix Sum**

- Exact Array: `[0, 2, 1, 0, 0]` _(Index 0 is unused)_
    
- Prefix Sum: `[0, 2, 3, 3, 3]`
    
    _This means: 2 pairs have a GCD $\le 1$. 3 pairs have a GCD $\le 2$._
    

**Phase 3: Binary Search Queries**

- **`q = 0` (We want the 1st smallest pair):**
    
    Binary search checks `GCD[mid]`. Is `GCD[1]` (which is 2) $\le 0$? **No.** Because 2 is strictly greater than 0, the target falls inside bucket `1`. Result is `1`.
    
- **`q = 2` (We want the 3rd smallest pair):**
    
    Is `GCD[1]` (which is 2) $\le 2$? **Yes.** Bucket 1 only holds up to the 2nd pair (indices 0 and 1). We must look higher. Is `GCD[2]` (which is 3) $\le 2$? **No.** Bucket 2 pushes the total to 3, which covers our query. Result is `2`.
    

Final Output: `[1, 2, 2]`

### Notes

- **Upper-Bound Binary Search Logic (`GCD[mid] <= q`):** This specific condition handles duplicate and empty buckets flawlessly. If there are empty buckets (e.g., `GCD` array looks like `[0, 2, 2, 2, 5]`), this condition ensures the pointers slide perfectly to the _right-most_ valid boundary. It ensures `l` only stops on the first bucket that actually contributed new pairs to the running total, skipping right over the empty ones.
    
- **The Harmonic Series Time Complexity:** The inner loop jumps by `i`. For $M = 100$, it runs $100/1 + 100/2 + 100/3 \dots 100/100$ times. Mathematically, this sum is modeled by the Harmonic series $O(\log M)$. Multiplying by the outer loop gives the highly efficient $O(M \log M)$ instead of a disastrous $O(M^2)$.