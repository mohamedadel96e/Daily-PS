package org.example.stack;

import java.util.Stack;

public class LargestRectangleInHistogram {
    public static void main(String[] args) {

    }

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int mxArea = -1;

        for (int i = 0; i < heights.length;i++) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                int height = heights[stack.pop()];
                int width = i - stack.peek() - 1;

                mxArea = Math.max(mxArea, width * height);
            }
            stack.push(i);
        }
        // What the stack actually have right now ?
        /*
        * the stack has indices of bars in increasing height order
        * */

        while (stack.peek() != -1) {
            int height = heights[stack.pop()];
            int width = heights.length - stack.peek() - 1;
            mxArea = Math.max(mxArea, width * height);
        }
        return mxArea;
    }
}
