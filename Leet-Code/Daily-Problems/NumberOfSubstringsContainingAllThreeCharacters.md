---
created: 2026-06-30T11:51
updated: 2026-06-30T12:44
Belongs To: "[[Daily-Questions]]"
---
# 1460. Number of Substrings Containing All Three Characters

|                |                                                                                                                                                                            |
| -------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **URL**        | [https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/](https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/) |
| **Difficulty** | 🟡 Medium                                                                                                                                                                  |
| **Topics**     | Hash Table, String, Sliding Window                                                                                                                                         |


---

## Problem

Given a string `s` consisting only of characters _a_, _b_ and _c_.

  Return the number of substrings containing **at least** one occurrence of all these characters _a_, _b_ and _c_.

   

 **Example 1:**

  
```

**Input:** s = "abcabc"
**Output:** 10
**Explanation:** The substrings containing at least one occurrence of the characters _a_, _b_ and _c are "_abc_", "_abca_", "_abcab_", "_abcabc_", "_bca_", "_bcab_", "_bcabc_", "_cab_", "_cabc_" _and_ "_abc_" _(**again**)_. _

```
  **Example 2:**

  
```

**Input:** s = "aaacb"
**Output:** 3
**Explanation:** The substrings containing at least one occurrence of the characters _a_, _b_ and _c are "_aaacb_", "_aacb_" _and_ "_acb_".__ _

```
  **Example 3:**

  
```

**Input:** s = "abc"
**Output:** 1

```
   

 **Constraints:**

  
  
- `3 <= s.length <= 5 x 10^4`  
- `s` only consists of _a_, _b_ or _c _characters.

---
### **Approach 1: Right-to-Left Tracking (Solution 1)**

### Intuition

If we fix the starting position of a substring, we just need to find the shortest valid string starting from that point. To do this, we need to know the closest occurrences of 'a', 'b', and 'c' to the right of our starting position. By iterating through the string backwards, we can keep a running memory of the most recently seen (which translates to the "closest" when moving left) indices for all three characters. Once we find the furthest of those three closest indices, we know exactly where our valid substring completes. Any substring extending beyond that completion point is also automatically valid.

### Algorithm

1. **Initialize Trackers:** Create variables `lastAIndex`, `lastBIndex`, and `lastCIndex` initialized to -1 to store the closest index of each character seen so far. Initialize `res` to 0.
    
2. **Reverse Iteration:** Loop through the string from the last character down to the first (index `s.length() - 1` to `0`).
    
3. **Update Indices:** For the current character, update its respective index variable to the current loop index `i`.
    
4. **Calculate Valid Substrings:** Check if all three index variables are no longer -1 (meaning we have seen at least one of each character to the right of our current position).
    
5. **Find the End Point:** Find the maximum among `lastAIndex`, `lastBIndex`, and `lastCIndex`. This maximum index represents the end of the _shortest_ valid substring starting at `i`.
    
6. **Accumulate:** Every character from this maximum index to the end of the string can be the end of a valid substring. Add `s.length() - maxIndex` to `res`.
    
7. Return `res`.
    

### Complexity

- **Time:** **O(N)**
    
    We iterate through the string of length N exactly once. Inside the loop, finding the maximum and doing basic arithmetic takes constant time.
    
- **Space:** **O(1)**
    
    We only use a few integer variables to track indices and the result. (Note: The string is accessed directly, avoiding extra space allocation).
    

### Solution


```java
class Solution {
    public int numberOfSubstrings(String s) {
        // Note: The 'freq' array is completely unused in the logic below
        int[] freq = new int[3]; 
        
        int lastAIndex = -1;
        int lastBIndex = -1;
        int lastCIndex = -1;
        int res = 0;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            freq[s.charAt(i) - 'a']++; // Dead code, can be removed
            
            // Update the closest seen index for the current character
            if (s.charAt(i) == 'a') lastAIndex = i;
            if (s.charAt(i) == 'b') lastBIndex = i;
            if (s.charAt(i) == 'c') lastCIndex = i;
            
            // If we have seen all three characters to the right
            if (lastAIndex != -1 && lastBIndex != -1 && lastCIndex != -1) {
                // Find the furthest required index to complete the set
                int temp = Math.max(lastAIndex, lastBIndex);
                int maxIndex = Math.max(lastCIndex, temp);
                
                // Add all valid substrings starting at i
                res += s.length() - maxIndex;
            }
        }
        return res;
    }
}
```

### Example Walkthrough

- **Input:** `s = "abcabc"`, Length = 6
    
- Iterate backward from `i = 5` to `0`. Initial states are all -1.
    

|**i**|**Char**|**lastA**|**lastB**|**lastC**|**Max Index Required**|**Valid Substrings (6 - max)**|**Total res**|
|---|---|---|---|---|---|---|---|
|**5**|c|-1|-1|5|None (missing a, b)|0|**0**|
|**4**|b|-1|4|5|None (missing a)|0|**0**|
|**3**|a|3|4|5|max(3, 4, 5) = **5**|6 - 5 = 1|**1**|
|**2**|c|3|4|2|max(3, 4, 2) = **4**|6 - 4 = 2|**3**|
|**1**|b|3|1|2|max(3, 1, 2) = **3**|6 - 3 = 3|**6**|
|**0**|a|0|1|2|max(0, 1, 2) = **2**|6 - 2 = 4|**10**|

**Result:** 10 valid substrings.

### Notes

- **Dead Code:** The `freq` array declared at the top of the function is updated but never actually used in any condition or calculation. It can be safely deleted to clean up the code.
    
- **The Math:** If a string length is 6, and the shortest valid substring starting at `i` ends at index 4, the valid ending indices are 4 and 5. The math `6 - 4` correctly results in 2 valid substrings.
    

### **Approach 2: Left-to-Right Tracking (AnotherSolution)**

### Intuition

Instead of fixing the start of the substring, this approach fixes the _end_ of the substring. As we read the string from left to right, we want to know: "If my substring ends here at this current character, how many valid starting points do I have?"

For a substring to be valid and end at our current position, it must start at or before the _oldest_ (minimum) index of our most recently seen 'a', 'b', and 'c'. Any starting point from index 0 up to that minimum index will encompass all three characters.

### Algorithm

1. **Initialize Array:** Create an `abc` array of size 3 to store the most recent (right-most) indices of 'a', 'b', and 'c'. Fill it with -1 initially.
    
2. **Forward Iteration:** Use a `while` loop with a `right` pointer moving from index `0` to the end of the string.
    
3. **Update Index:** Map the current character to index 0, 1, or 2 using `ch[right] - 'a'`. Update the `abc` array at that index to the current `right` position.
    
4. **Find Minimum Index:** Iterate through the 3 values in the `abc` array to find the minimum index. This represents the earliest character of the required trio.
    
5. **Accumulate:** If `minIndex` is not -1, it means we have all three characters. Any substring starting from index `0` up to `minIndex` and ending at `right` is valid. The number of such substrings is exactly `minIndex + 1`. We add this to `count`.
    
6. Return `count`.
    

### Complexity

- **Time:** **O(N)**
    
    We traverse the string left to right exactly once. Inside the loop, checking the minimum of an array of size 3 takes constant **O(1)** time.
    
- **Space:** **O(N)** or **O(1)**
    
    Converting the string to a char array `ch` creates a new array of length N, making the space complexity **O(N)**. If we used `s.charAt(right)` instead, the space complexity would be optimized to **O(1)**.
    

### Solution


```java
class AnotherSolution {
    public int numberOfSubstrings(String s) {
        char[] ch = s.toCharArray();
        int[] abc = new int[3];
        Arrays.fill(abc, -1);
        
        int count = 0, right = 0;
        
        while(right < ch.length) {
            // Update the most recent index for the current character
            abc[ch[right] - 'a'] = right;
            
            // Find the oldest required character's index
            int minIndex = Integer.MAX_VALUE;
            for(int i = 0; i < 3; i++){
                minIndex = Math.min(minIndex, abc[i]);
            }
            
            // Add all valid substrings ending at 'right'
            // If we haven't seen all chars yet, minIndex is -1, so we add 0
            count += (minIndex + 1);
            right++;
        }
        return count;
    }
}
```

### Example Walkthrough

- **Input:** `s = "abcabc"`, Length = 6
    
- Iterate forward from `right = 0` to `5`. Array `abc` starts as `[-1, -1, -1]`.
    

|**right**|**Char**|**abc array ([a, b, c])**|**minIndex**|**Substrings Ending Here (min + 1)**|**Total count**|
|---|---|---|---|---|---|
|**0**|a|`[0, -1, -1]`|-1|0|**0**|
|**1**|b|`[0, 1, -1]`|-1|0|**0**|
|**2**|c|`[0, 1, 2]`|0|0 + 1 = 1|**1**|
|**3**|a|`[3, 1, 2]`|1|1 + 1 = 2|**3**|
|**4**|b|`[3, 4, 2]`|2|2 + 1 = 3|**6**|
|**5**|c|`[3, 4, 5]`|3|3 + 1 = 4|**10**|

**Result:** 10 valid substrings.

### Notes

- **The Math:** If our current position is index 4 (`b`), and our most recent indices are `a:3`, `b:4`, `c:2`, the minimum is 2. Substrings ending at index 4 must start at index 2 or earlier to include all three characters. The valid starting indices are 0, 1, and 2. The amount of valid starting indices is `2 + 1 = 3`.
    
- **Elegant Edge Handling:** Initializing the array with -1 is a brilliant trick here. If we haven't seen all characters yet, the minimum will be -1. Adding `minIndex + 1` mathematically results in adding 0 to our count without needing an explicit `if` statement to check if we've seen everything.


### Another Better Solution
```java
class Solution {  
    public int numberOfSubstrings(String s) {  
        int res = 0;  
        int[] p = {-1, -1, -1};  
  
        for (int i = 0; i < s.length(); i++) {  
            p[(s.charAt(i) & 31) - 1] = i;  
            res += Math.min(p[0], Math.min(p[1], p[2])) + 1;  
        }  
  
        return res;  
    }  
}
```