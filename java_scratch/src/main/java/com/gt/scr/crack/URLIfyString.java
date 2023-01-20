package com.gt.scr.crack;

/**
 * Problem: Write a method to replace all spaces in a string with'%20'You may assume that the string has sufficient space at the end
 * to hold the additional characters, and that you are given the "true" length of the string,
 *
 * Idea is to start from the end of the string and move characters within the array until we find a space.
 * When we find a space, we replace it with '%20'.
 */
public class URLIfyString {
    public String doWork(String input, int trueLength) {
        char[] chars = input.toCharArray();
        int fullLength = input.length() - 1;

        for (int i = trueLength - 1; i >=0 && fullLength >= 0; i--) {
            if (chars[i] != ' ') {
                chars[fullLength--] = chars[i];
            } else {
                chars[fullLength--] = '0';
                chars[fullLength--] = '2';
                chars[fullLength--] = '%';
            }
        }

        return new String(chars);
    }
}
