---
created: 2026-06-19T09:56
updated: 2026-06-19T10:07
---
# 1833. Find the Highest Altitude

|                |                                                                                                                      |
| -------------- | -------------------------------------------------------------------------------------------------------------------- |
| **URL**        | [https://leetcode.com/problems/find-the-highest-altitude/](https://leetcode.com/problems/find-the-highest-altitude/) |
| **Difficulty** | 🟢 Easy                                                                                                              |
| **Topics**     | Array, Prefix Sum                                                                                                    |
| **Status**     | Finished                                                                                                             |

---

## Problem

There is a biker going on a road trip. The road trip consists of `n + 1` points at different altitudes. The biker starts his trip on point `0` with altitude equal `0`.

  You are given an integer array `gain` of length `n` where `gain[i]` is the **net gain in altitude** between points `i`​​​​​​ and `i + 1` for all (`0 <= i < n)`. Return _the **highest altitude** of a point._

   

 **Example 1:**

  
```

**Input:** gain = [-5,1,5,0,-7]
**Output:** 1
**Explanation:** The altitudes are [0,-5,-4,1,1,-6]. The highest is 1.

```
  **Example 2:**

  
```

**Input:** gain = [-4,-3,-2,-1,4,3,2]
**Output:** 0
**Explanation:** The altitudes are [0,-4,-7,-9,-10,-6,-3,-1]. The highest is 0.

```
   

 **Constraints:**

  
  
- `n == gain.length`  
- `1 <= n <= 100`  
- `-100 <= gain[i] <= 100`

---

## Approach

### Intuition

> _It's a very easy problem depending on the prefix sum we just create a variable for storing the highest gain and a cumulative variable to store the prefix sum along the way_

### Algorithm

1. Prefix Sum

### Complexity

|           |      |
| --------- | ---- |
| **Time**  | O(n) |
| **Space** | O(1) |

---

## Solution

```java
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
```

---

## Notes

-
