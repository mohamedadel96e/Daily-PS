---
created: 1970-01-01T02:00
updated: 2026-07-14T13:50
Belongs To: "[[Daily-Questions]]"
---
# 3608. Find the Number of Subsequences With Equal GCD

|                |                                                                                                                                                                |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **URL**        | [https://leetcode.com/problems/find-the-number-of-subsequences-with-equal-gcd/](https://leetcode.com/problems/find-the-number-of-subsequences-with-equal-gcd/) |
| **Difficulty** | 🔴 Hard                                                                                                                                                        |
| **Topics**     | Array, Math, Dynamic Programming, Number Theory                                                                                                                |


---

## Problem

You are given an integer array `nums`.

  Your task is to find the number of pairs of **non-empty** subsequences `(seq1, seq2)` of `nums` that satisfy the following conditions:

  
  
- The subsequences `seq1` and `seq2` are **disjoint**, meaning **no index** of `nums` is common between them.  
- The GCD of the elements of `seq1` is equal to the GCD of the elements of `seq2`. 
  Return the total number of such pairs.

  Since the answer may be very large, return it **modulo** `10^9 + 7`.

   

 **Example 1:**

   **Input:** nums = [1,2,3,4]

  **Output:** 10

  **Explanation:**

  The subsequence pairs which have the GCD of their elements equal to 1 are:

  
  
- `([**1**, 2, 3, 4], [1, **2**, **3**, 4])`  
- `([**1**, 2, 3, 4], [1, **2**, **3**, **4**])`  
- `([**1**, 2, 3, 4], [1, 2, **3**, **4**])`  
- `([**1**, **2**, 3, 4], [1, 2, **3**, **4**])`  
- `([**1**, 2, 3, **4**], [1, **2**, **3**, 4])`  
- `([1, **2**, **3**, 4], [**1**, 2, 3, 4])`  
- `([1, **2**, **3**, 4], [**1**, 2, 3, **4**])`  
- `([1, **2**, **3**, **4**], [**1**, 2, 3, 4])`  
- `([1, 2, **3**, **4**], [**1**, 2, 3, 4])`  
- `([1, 2, **3**, **4**], [**1**, **2**, 3, 4])` 
   **Example 2:**

   **Input:** nums = [10,20,30]

  **Output:** 2

  **Explanation:**

  The subsequence pairs which have the GCD of their elements equal to 10 are:

  
  
- `([**10**, 20, 30], [10, **20**, **30**])`  
- `([10, **20**, **30**], [**10**, 20, 30])` 
   **Example 3:**

   **Input:** nums = [1,1,1,1]

  **Output:** 50

    

 **Constraints:**

  
  
- `1 <= nums.length <= 200`  
- `1 <= nums[i] <= 200`

---

### Intuition

Imagine you have two empty buckets (Sequence 1 and Sequence 2). As you go through the list of numbers one by one, you must decide what to do with each number. You have exactly three choices:

1. Throw it in the trash (ignore it).
    
2. Toss it into Bucket 1.
    
3. Toss it into Bucket 2.
    

The problem asks us to find how many ways we can do this so that eventually, the Greatest Common Divisor (GCD) of Bucket 1 perfectly matches the GCD of Bucket 2.

Because we only care about the final GCD—and not the actual numbers sitting inside the buckets—we don't need to remember the numbers. We only need to remember the _current GCD_ of each bucket. Since the numbers never exceed 200, the GCD will never exceed 200. This means we can just use a 2D grid where the rows represent Bucket 1's GCD and the columns represent Bucket 2's GCD, and simply count how many ways we can reach each combination.

### Algorithm

1. **The Grid Setup:** We create two 2D grids, `cur` and `nxt`, of size $201 \times 201$.
    
    - `cur[g1][g2]` holds the number of ways to have a GCD of `g1` in the first sequence and `g2` in the second sequence _before_ we process the current number.
        
    - `nxt[g1][g2]` will hold the new combinations _after_ we process the current number.
        
2. **The Starting Point:** We set `cur[0][0] = 1`. We use `0` to represent an "empty" sequence. There is exactly 1 way to have two empty sequences before we start.
    
3. **Process Each Number:** For every number `x` in the array:
    
    - First, wipe the `nxt` grid clean with 0s so it's ready for fresh calculations.
        
    - Look at every possible combination of `(g1, g2)` on our `cur` grid. If `cur[g1][g2]` is greater than 0, it means we have active paths that reached this state.
        
    - **Simulate the 3 Choices:** Take the number of ways we reached `(g1, g2)` and distribute them into the `nxt` grid based on our choices:
        
        - **Ignore:** The GCDs stay the same. Add the ways to `nxt[g1][g2]`.
            
        - **Add to Seq 1:** The new GCD for sequence 1 becomes `gcd(g1, x)`. Add the ways to `nxt[ng1][g2]`.
            
        - **Add to Seq 2:** The new GCD for sequence 2 becomes `gcd(g2, x)`. Add the ways to `nxt[g1][ng2]`.
            
4. **Advance the State:** Swap the `cur` and `nxt` grids. Now `cur` holds the updated states, and `nxt` is ready to be wiped and reused for the next number.
    
5. **Count the Winners:** After processing all numbers, we look diagonally across our grid (`cur[1][1]`, `cur[2][2]`, up to `cur[200][200]`). We add up all the ways where both sequences are non-empty and have the exact same GCD.
    

### Complexity

- **Time:** $\mathcal{O}(N \cdot M^2)$
    
    $N$ is the amount of numbers (up to 200). $M$ is the maximum GCD (200). For every single number, we check a $200 \times 200$ grid. In the worst case, this is roughly $200 \times 200 \times 200 = 8,000,000$ operations. This easily runs within the time limit.
    
- **Space:** $\mathcal{O}(M^2)$
    
    By only keeping the current step (`cur`) and the next step (`nxt`), we only need memory for two $201 \times 201$ arrays. This is highly efficient and uses very little RAM.
    

### Solution

```java
class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        // cur: Combinations before processing the current number
        // nxt: Combinations after processing the current number
        int[][] cur = new int[201][201];
        int[][] nxt = new int[201][201];

        // 0 means the sequence is currently empty. 
        cur[0][0] = 1;

        for (int x : nums) {
            // Clear the slate for the next round of calculations
            for (int i = 0; i <= 200; i++) {
                Arrays.fill(nxt[i], 0);
            }

            // Look at every possible state we currently have
            for (int g1 = 0; g1 <= 200; g1++) {
                for (int g2 = 0; g2 <= 200; g2++) {
                    
                    // If we haven't reached this combination yet, skip it
                    if (cur[g1][g2] == 0) continue;

                    long ways = cur[g1][g2];

                    // Choice 1: Throw 'x' in the trash
                    nxt[g1][g2] = (int) ((nxt[g1][g2] + ways) % MOD);

                    // Choice 2: Put 'x' in Sequence 1
                    // If Seq 1 is empty (g1 == 0), its new GCD is just 'x'
                    int ng1 = (g1 == 0) ? x : gcd(g1, x);
                    nxt[ng1][g2] = (int) ((nxt[ng1][g2] + ways) % MOD);

                    // Choice 3: Put 'x' in Sequence 2
                    // If Seq 2 is empty (g2 == 0), its new GCD is just 'x'
                    int ng2 = (g2 == 0) ? x : gcd(g2, x);
                    nxt[g1][ng2] = (int) ((nxt[g1][ng2] + ways) % MOD);
                }
            }

            // Swap the arrays. 'cur' now holds the results of this round.
            int[][] temp = cur;
            cur = nxt;
            nxt = temp;
        }

        long ans = 0;
        // Sum up all combinations where Seq 1 and Seq 2 have the exact same GCD.
        // We start at 1 to ignore the [0][0] state (which means both are empty).
        for (int g = 1; g <= 200; g++) {
            ans = (ans + cur[g][g]) % MOD;
        }

        return (int) ans;
    }

    // Standard Euclidean algorithm to find the Greatest Common Divisor
    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```

### Example Walkthrough

Let's see what happens when the very first number in our array is `10`.

- **Start:** `cur[0][0]` has 1 way. Everything else is 0.
    
- **Process 10:** The loops hit `g1 = 0, g2 = 0`. It sees `ways = 1`. It branches out into the `nxt` grid:
    
    1. **Ignore 10:** `nxt[0][0]` gets 1 way.
        
    2. **Seq 1 gets 10:** Seq 1 was empty, so its GCD becomes 10. `nxt[10][0]` gets 1 way.
        
    3. **Seq 2 gets 10:** Seq 2 was empty, so its GCD becomes 10. `nxt[0][10]` gets 1 way.
        
- **End of round:** We swap grids. Now `cur` knows there is 1 way to have two empty sequences, 1 way to have a 10 in Seq 1, and 1 way to have a 10 in Seq 2.
    
    When the next number comes along, the algorithm will branch out from all _three_ of those states, exploring every possible future simultaneously.
    

### Notes

- **The Empty Sequence Trick:** Using `0` to represent an empty sequence is a brilliant math shortcut. Mathematically, the GCD of 0 and any number $X$ is just $X$. By writing `(g1 == 0) ? x : gcd(g1, x)`, we correctly set the sequence's first number as its starting GCD, completely avoiding messy boolean arrays or extra logic to track if a sequence is empty.
    
- **Modulo at Every Step:** You'll notice `% MOD` on every single addition. Because the number of combinations can grow exponentially, they would quickly exceed the maximum limit of an integer. Taking the modulo at every step ensures the numbers stay small and safe from overflow while keeping the math perfectly accurate.

### The Evolution of the Algorithm

The solution conceptually evolves through three stages, each improving upon the last.

#### 1. Recursion + Memoization (Top-Down DP)

We start at index `0` and make a choice for the current element: ignore it, put it in `seq1`, or put it in `seq2`. We recursively call the function for `index + 1` with the updated GCDs.

- **Base Case:** When we reach the end of the array (`idx == n`), we check if `g1 == g2`. We also ensure `g1 != 0` so we don't count empty sequences. If valid, return `1` way; otherwise, `0`.
    
- **Memoization:** Many paths lead to the same `(idx, g1, g2)` state. We store the result in a 3D array `dp[idx][g1][g2]` so we never calculate the exact same subproblem twice.
    

#### 2. Iterative 3D DP (Bottom-Up DP)

Recursion has overhead (call stack size, function call time). We can invert the logic to build from the bottom up.

- We initialize `dp[0][0][0] = 1` (1 way to have two empty sequences before processing any elements).
    
- For every element at index `i`, we look at all reachable states in `dp[i]`. For each state `(g1, g2)` that has $>0$ ways, we "push" those ways forward into `dp[i+1]` by simulating our three choices.
    
- Finally, we sum up all `dp[n][g][g]` where $g \ge 1$.
    

#### 3. Space Optimized 2D DP

In the 3D DP, notice the transition: row `i + 1` is calculated looking **only** at row `i`. Rows `i - 1`, `i - 2`, etc., are never accessed again.

- Storing the entire $N \times 201 \times 201$ array wastes memory.
    
- We can replace the `i` dimension with just two $201 \times 201$ arrays: `cur` (representing `dp[i]`) and `nxt` (representing `dp[i+1]`).
    
- After processing an element, we overwrite `cur` with `nxt` and reuse the memory, dropping the space requirement drastically.
    

### The Optimizations Explained

1. **State Abstraction (The core optimization):** Instead of storing arrays or subsets, the state is heavily abstracted to `(g1, g2)`. Because the max array value is $200$, the state space is capped at $201 \times 201 = 40,401$ combinations per index, making an impossible $3^{200}$ problem easily solvable.
    
2. **The `0` Placeholder Trick:** We use a GCD of $0$ to represent an empty sequence. Because $\gcd(0, x) = x$, when we add the very first element to an empty sequence, the math automatically sets the sequence's GCD to that element without requiring complex `if (isEmpty)` boolean flags throughout the logic.
    
3. **The Zero-Check Optimization:** Inside the loop, you see `int ng1 = (g1 == 0) ? x : gcd(g1, x);`. Standard Euclidean algorithms evaluate $\gcd(0, x)$ correctly, but putting this ternary check prevents the program from entering the `while` loop inside the `gcd` helper method, saving millions of fractional CPU cycles over the nested loops.
    
4. **Modulo Addition:** Instead of calculating a massive number and applying `% MOD` at the very end (which would overflow standard 64-bit integers), the modulo is applied at every single addition step: `(nxt + ways) % MOD`. This keeps values strictly within integer bounds.
    
5. **Memory Swapping (Pointer Swap):** In the 2D DP, `cur = nxt` and `nxt = temp` just swaps the memory references (pointers) in $\mathcal{O}(1)$ time. This avoids copying thousands of elements from one array to another.
    

### Complexity

- **Recursion + Memoization:**
    
    - Time: $\mathcal{O}(N \cdot M^2)$ where $N$ is array length and $M$ is max value ($200$).
        
    - Space: $\mathcal{O}(N \cdot M^2)$ for the 3D array, plus $\mathcal{O}(N)$ for the recursion call stack.
        
- **Iterative 3D DP:**
    
    - Time: $\mathcal{O}(N \cdot M^2)$
        
    - Space: $\mathcal{O}(N \cdot M^2)$ exactly. No recursion stack overhead.
        
- **Space Optimized 2D DP:**
    
    - Time: $\mathcal{O}(N \cdot M^2)$
        
    - Space: $\mathcal{O}(M^2)$. We only hold two arrays of size $201 \times 201$, saving a massive amount of RAM.
        

### The 3 Solutions

#### 1. Recursion + Memoization (Top-Down)

```java
class Solution {
    private static final int MOD = 1_000_000_007;
    private int n;
    private int[][][] dp;

    private int solve(int idx, int g1, int g2, int[] nums) {
        if (idx == n) {
            return (g1 != 0 && g1 == g2) ? 1 : 0;
        }

        if (dp[idx][g1][g2] != -1) return dp[idx][g1][g2];

        long ans = 0;

        // 1. Ignore
        ans = solve(idx + 1, g1, g2, nums);

        // 2. Put in seq1
        int ng1 = (g1 == 0) ? nums[idx] : gcd(g1, nums[idx]);
        ans = (ans + solve(idx + 1, ng1, g2, nums)) % MOD;

        // 3. Put in seq2
        int ng2 = (g2 == 0) ? nums[idx] : gcd(g2, nums[idx]);
        ans = (ans + solve(idx + 1, g1, ng2, nums)) % MOD;

        return dp[idx][g1][g2] = (int) ans;
    }

    public int subsequencePairCount(int[] nums) {
        n = nums.length;
        dp = new int[n + 1][201][201];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 200; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        return solve(0, 0, 0, nums);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```

#### 2. Iterative 3D DP (Bottom-Up

```java
class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int n = nums.length;
        int[][][] dp = new int[n + 1][201][201];
        dp[0][0][0] = 1;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            for (int g1 = 0; g1 <= 200; g1++) {
                for (int g2 = 0; g2 <= 200; g2++) {
                    if (dp[i][g1][g2] == 0) continue;

                    long ways = dp[i][g1][g2];

                    // 1. Ignore
                    dp[i + 1][g1][g2] = (int) ((dp[i + 1][g1][g2] + ways) % MOD);

                    // 2. Put in seq1
                    int ng1 = (g1 == 0) ? x : gcd(g1, x);
                    dp[i + 1][ng1][g2] = (int) ((dp[i + 1][ng1][g2] + ways) % MOD);

                    // 3. Put in seq2
                    int ng2 = (g2 == 0) ? x : gcd(g2, x);
                    dp[i + 1][g1][ng2] = (int) ((dp[i + 1][g1][ng2] + ways) % MOD);
                }
            }
        }

        long ans = 0;
        for (int g = 1; g <= 200; g++) {
            ans = (ans + dp[n][g][g]) % MOD;
        }
        return (int) ans;
    }
    
    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```

#### 3. Space Optimized 2D DP (Final Architecture)

```java
class Solution {
    private static final int MOD = 1_000_000_007;

    public int subsequencePairCount(int[] nums) {
        int[][] cur = new int[201][201];
        int[][] nxt = new int[201][201];

        cur[0][0] = 1;

        for (int x : nums) {
            // Clear the 'next' array before processing the current number
            for (int i = 0; i <= 200; i++) {
                Arrays.fill(nxt[i], 0);
            }

            for (int g1 = 0; g1 <= 200; g1++) {
                for (int g2 = 0; g2 <= 200; g2++) {
                    if (cur[g1][g2] == 0) continue;

                    long ways = cur[g1][g2];

                    // 1. Ignore
                    nxt[g1][g2] = (int) ((nxt[g1][g2] + ways) % MOD);

                    // 2. Put in seq1
                    int ng1 = (g1 == 0) ? x : gcd(g1, x);
                    nxt[ng1][g2] = (int) ((nxt[ng1][g2] + ways) % MOD);

                    // 3. Put in seq2
                    int ng2 = (g2 == 0) ? x : gcd(g2, x);
                    nxt[g1][ng2] = (int) ((nxt[g1][ng2] + ways) % MOD);
                }
            }

            // Pointer swap - cur becomes the newly calculated layer
            int[][] temp = cur;
            cur = nxt;
            nxt = temp;
        }

        long ans = 0;
        for (int g = 1; g <= 200; g++) {
            ans = (ans + cur[g][g]) % MOD;
        }
        return (int) ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```