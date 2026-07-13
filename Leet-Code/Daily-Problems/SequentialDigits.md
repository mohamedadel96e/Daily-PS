---
created: 1970-01-01T02:00
updated: 2026-07-13T21:02
---
# 1212. Sequential Digits

|                |                                                                                                                              |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| **URL**        | [https://leetcode.com/problems/sequential-digits/description/](https://leetcode.com/problems/sequential-digits/description/) |
| **Difficulty** | 🟡 Medium                                                                                                                    |
| **Topics**     | Enumeration                                                                                                                  |


---

## Problem

An integer has _sequential digits_ if and only if each digit in the number is one more than the previous digit.

  Return a **sorted** list of all the integers in the range `[low, high]` inclusive that have sequential digits.

   

 **Example 1:**

 
```
**Input:** low = 100, high = 300
**Output:** [123,234]

```
**Example 2:**

 
```
**Input:** low = 1000, high = 13000
**Output:** [1234,2345,3456,4567,5678,6789,12345]

```
  

 **Constraints:**

  
  
- `10 <= low <= high <= 10^9`

---

## Approach

### Intuition

When looking at the constraints, the maximum possible value is $10^9$. If we try to think of every possible sequential digit number up to $10^9$ (like 12, 123, 234, ..., 123456789), there actually aren't that many.

- There are 9 single-digit numbers (1-9)
    
- There are 8 two-digit numbers (12, 23, 34...)
    
- There are 7 three-digit numbers (123, 234...)
    
- ...and only 1 nine-digit number (123456789).
    

In total, there are exactly 45 sequential digit numbers. Because this list is so small and fixed, generating them on the fly for every test case is inefficient. Instead, we can precompute all 45 numbers exactly once when the program starts. To build them, we can use a Breadth-First Search (BFS) approach: take an existing sequential number (like `12`), look at its last digit (`2`), and if it's less than 9, append the next digit (`3`) to create a new sequential number (`123`).

### Algorithm

1. **Static Initialization:** Declare a static array `q` of size 45. Use a `static` block so this precomputation runs only once when the class is loaded into memory, making subsequent function calls lightning-fast.
    
2. **Seed the Array:** Populate the first 9 elements of the array with the single digits `1` through `9`. Set a pointer `n = 9` to track the current size.
    
3. **Generate by Expanding:** Iterate over the array from `i = 0` up to the ever-growing `n`.
    
    - For the current number `q[i]`, find its last digit using modulo 10 (`d = q[i] % 10`).
        
    - If the last digit is less than 9, we can extend it. Multiply the current number by 10 and add `d + 1` (e.g., `12` becomes `12 * 10 + 3 = 123`).
        
    - Add this newly formed number to the end of the array `q[n++]`.
        
4. **Naturally Sorted:** Because we process all 1-digit numbers, which generate all 2-digit numbers, which generate all 3-digit numbers, the array `q` is naturally populated in perfect ascending order.
    
5. **Filter for the Answer:** In the actual `sequentialDigits` method, simply iterate through the precomputed array `q` and add any number that falls within the `[low, high]` range to the result list.
    

### Complexity

- **Time:** $\mathcal{O}(1)$
    
    The precomputation runs exactly once and takes 45 iterations. The actual function `sequentialDigits` merely iterates over the 45 elements of the array, taking constant time regardless of the input size.
    
- **Space:** $\mathcal{O}(1)$
    
    We use a fixed-size array of length 45. The memory footprint does not scale with the input.
    

### Solution

```java
class Solution {
    // There are exactly 45 sequential numbers possible
    static final int[] q = new int[45];

    // Static block precomputes the array once per class load
    static {
        int n = 0;

        // Seed with 1-9
        for (int i = 1; i < 10; i++)
            q[n++] = i;

        // Expand existing numbers to create larger ones
        for (int i = 0; i < n; i++) {
            int d = q[i] % 10; // Get the last digit

            // If it can be extended, append the next sequential digit
            if (d < 9) 
                q[n++] = q[i] * 10 + d + 1;
        }
    }

    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();

        // Filter the precomputed, sorted array for the requested range
        for (int x : q)
            if (x >= low && x <= high)
                res.add(x);

        return res;
    }
}
```

### Example Walkthrough

- **Input:** `low = 100`, `high = 300`
    

**Phase 1: Precomputation (Happens in background)**

- Array starts as `[1, 2, 3, 4, 5, 6, 7, 8, 9]`
    
- Pointer `i` is at 0 (value `1`). Last digit is `1`. We create `1 * 10 + 2 = 12`. Add to array.
    
- ...
    
- Pointer `i` reaches 8 (value `9`). Last digit is `9`. Ignored (`d < 9` is false).
    
- Pointer `i` reaches 9 (value `12`). Last digit is `2`. We create `12 * 10 + 3 = 123`. Add to array.
    
- Final array `q` looks like: `[1, 2, ..., 9, 12, 23, ..., 89, 123, 234, ..., 123456789]`
    

**Phase 2: Execution**

- Iterate through `q`.
    
- Ignore `1` through `89` (less than 100).
    
- See `123`. It is $\ge 100$ and $\le 300$. Add to `res`.
    
- See `234`. It is $\ge 100$ and $\le 300$. Add to `res`.
    
- See `345`. It is $> 300$. The condition fails. (We could theoretically `break` early here for an ultra-micro optimization, but 45 iterations is already negligible).
    

**Result:** `[123, 234]`

### Notes

- **The Static Block Trick:** In competitive programming and LeetCode, testing platforms often run the same class file against hundreds of test cases. By putting the heavy lifting in a `static` initialization block, the logic runs _once_ when the system loads your solution class. When the platform instantiates your class for test case 1, 2, 3, and so on, the array is already fully built, resulting in nearly 0ms execution times.