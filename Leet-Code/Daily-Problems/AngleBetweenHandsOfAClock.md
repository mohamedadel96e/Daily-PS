---
created: 2026-06-18T20:13
updated: 2026-06-18T20:34
Belongs To: "[[Problem Solving]]"
---
# 1446. Angle Between Hands of a Clock

|                |                                                                                                                                |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| **URL**        | [https://leetcode.com/problems/angle-between-hands-of-a-clock/](https://leetcode.com/problems/angle-between-hands-of-a-clock/) |
| **Difficulty** | 🟡 Medium                                                                                                                      |
| **Topics**     | Math                                                                                                                           |
| **Status**     | ⬜ Not Started                                                                                                                  |

---

## Problem

Given two numbers, `hour` and `minutes`, return _the smaller angle (in degrees) formed between the _`hour`_ and the _`minute`_ hand_.

  Answers within `10^-5` of the actual value will be accepted as correct.

   

 **Example 1:**

  
```

**Input:** hour = 12, minutes = 30
**Output:** 165

```
  **Example 2:**

  
```

**Input:** hour = 3, minutes = 30
**Output:** 75

```
  **Example 3:**

  
```

**Input:** hour = 3, minutes = 15
**Output:** 7.5

```
   

 **Constraints:**

  
  
- `1 <= hour <= 12`  
- `0 <= minutes <= 59`

---

## Approach

## Approach 1: Modular Time

```java
class Solution {
    public double angleClock(int hour, int minutes) {
        double x = hour + minutes / 60.0;
        double diff = (11.0 * x) % 12.0;
        return Math.min(diff, 12.0 - diff) * 30.0;
    }
}
```
## Intuition

Any given time x can be represented with a coordinate, so we're going to look at it on a graph.

- The x and y coordinates are between 0 and 12 representing the hour hand and minute hand respectively.

## For example:

- 12:00, 12:30, and 12:45 can be represented as:

![Screenshot 2026-06-18 074731.png](https://assets.leetcode.com/users/images/cc2a9e4a-a1d0-43be-9d48-0f2ec9025fd9_1781740074.6913083.png)

Since the clock moves at a constant speed, the first hour can be represented by the line:

y=12x​

---

For every hour, the minute hand returns to zero, so the graph becomes:

y=12x mod 12​

![Screenshot 2026-06-18 075320.png](https://assets.leetcode.com/users/images/6016db91-d4d6-48c3-9d60-f674da28abe0_1781740432.001165.png)

---

### Hour Hand

The hour hand moves by 1 unit per hour.

To plot time in hours (x) on the coordinate grid, we represent the input time in decimal form:

x=hour+60minutes​​

![Screenshot 2026-06-18 080745.png](https://assets.leetcode.com/users/images/9967eac2-a4c5-4086-ac71-c26fe701054c_1781741277.4735959.png)

![Untitled-115.png](https://assets.leetcode.com/users/images/997b17c5-0f4d-44d7-8d2f-fca5e8507c14_1781758249.6958587.png)

> The points where two equations intersects are the times when are the hour and minute hands of a clock are superimposed (the angle is zero).

---

### Hour Hand vs Minute Hand

If we count the raw number of minutes that have passed since the initial time, 12:00, against the hours, we know that the minute hand moves 11 times faster.

![Screenshot 2026-06-18 084212.png](https://assets.leetcode.com/users/images/44588c24-23a8-4c4e-ae05-f99ff99d8065_1781743345.050098.png)

We wrap it back using modulo to find the actual distance between the clock hands.

diff=11x  mod  12​

---

## Angle Between Hands

Let Hour=2, Min=0:

∴x=2​

Compute the unit difference:

diff1​diff1​​=11x  mod  12=(11×2) mod 12=2​​diff2​diff2​=12−diff1​=10

![Untitled-3.png](https://assets.leetcode.com/users/images/432988e1-205e-4dc1-870c-7954bdc18077_1781758163.2340817.png)

![Untitled-5.png](https://assets.leetcode.com/users/images/95e92fd9-4658-43b5-ac22-3c5be2252025_1781757901.6927896.png)

The angle between adjacent hour marks is simply 12360​=30∘.

So we multiply it by the minimum difference to find the actual angle:

result=2×30∘=60∘​​

---

Let Hour=7, Min=15:

∴x=7.25​

Compute the unit difference:

diff1​diff1​​=11x  mod  12=(11×7.25) mod 12=7.75​diff2​diff2​=12−diff1​=4.25​

![Screenshot 2026-06-18 122009.png](https://assets.leetcode.com/users/images/123cf3b5-9f76-4ba5-b5b9-980f60468e92_1781756435.026878.png)

![dfgfhdfh.png](https://assets.leetcode.com/users/images/6c5b4222-de1e-485e-ab38-1dd7dcb31422_1781758355.6940525.png)

result=4.25×30∘=127.5∘​​

---

## Approach 2: Trigonometry

```cpp
class Solution {
public:
    double angleClock(int h, int m) {
        return acos(cos((330 * h + 5.5 * m) * 0.0174532925)) * 57.2957795;
    }
};
```

Show Explanation

## The Angular difference θ

- x represents the exact time in hours expressed in decimal form.

x=hour+60minutes​​

- 11x is the raw, un-wrapped distance between the minute and hour hands measured in hour units.
    
- The angle between two adjacent hour mark is 30∘.
    

Thus, the unwrapped angular difference θ between two hand for any given hour and minute:

θθ​=11×(hour+60minutes​)×30∘=11×(30∘×hour+0.5∘×minutes)=330∘×hour+5.5∘×minutes​​

---

### Trigometric Reduction

When tracking the hands over time, the raw relative angle difference (θ) accumulates continuously.

In modular arithmetic, two angles are congruent modulo 360∘ if their difference is an integer multiple of 360∘:

θ1​≡θ2​(mod360∘)​

---

### Cosine Equivalence

Instead of using modular arithmetic, we can utilize the cosine function, which naturally honors this equivalence because it is periodic with a period of 2π radians (360∘):

cos(θ)=cos(θ+360∘⋅k),k∈Z​

This identity forces angle above 360∘ to instantly collapse into its base congruent representative between 0∘ and 360∘.

---

Cosine also possesses a reflective symmetry across the x−axis:

cos(θ)=cos(360∘−θ)​

For example:

- Angle of 200∘ produces the exact same cosine value as an angle of 160∘.

---

### Arccosine Inversion

Any smaller angle difference is than 180∘, so the results must be less than 180∘.

Conveniently, we can apply the inverse cosine or arccosine function.

Since cos−1(θ) is defined strictly as a one-to-one mapping that outputs values within the principal interval:

[0,180∘]​

---

Note that built-in trigonometric functions uses radian as input:

Hence, we are required to use constant factors to convert degrees to radians, then vise-versa:

rad​=deg×180∘π​≈deg×0.0174532925​​​deg​=rad×π180∘​=rad×57.2957795∘​​​​

> This algorithm runs in constant time but might still be magnitude slower than arithmetic approach, because trig functions are computationally expensive.

---

**Time Complexity:** O(1)  
**Space Complexity:** O(1)