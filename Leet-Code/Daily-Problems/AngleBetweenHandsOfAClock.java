package org.example;

/**
 * #1446 · Angle Between Hands of a Clock
 * URL        : https://leetcode.com/problems/angle-between-hands-of-a-clock/
 * Difficulty : Medium
 */
public class AngleBetweenHandsOfAClock {

    class Solution {
        public double angleClock(int hour, int minutes) {
            double hours = hour + minutes / 60.0;
            double difference  = (11 * hours) % 12.0;
            return Math.min(difference, 12.0 - difference) * 30.0;
        }
    }

    public static void main(String[] args) {
        AngleBetweenHandsOfAClock sol = new AngleBetweenHandsOfAClock();
    }
}
