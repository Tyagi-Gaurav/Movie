package com.gt.scr.crack;

import java.util.*;

/**
 * Assuming this is only for ASCII characters which are only 256
 *
 * Approach 1: Sort and check consecutive characters. O(n log(n))
 * Approach 2: Keep a Set and then check element against the set. O(n) but uses an additional data structure.
 * Approach 3: (Without a data structure) check each character in a double for loop. If any pair value is 0 then it's not unique. O(n^2).
 * Approach 4: Use Bit vector
 */
public class CheckUnique {
    public boolean hasUniqueCharacters1(String value) {
        if (value.length() > 256) {
            return false;
        }

        char[] chars = value.toCharArray();
        Arrays.sort(chars);

        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == chars[i+1]) {
                return false;
            }
        }

        return true;
    }

    public boolean hasUniqueCharacters2(String value) {
        int length = value.length();
        if (length > 256) {
            return false;
        }
        Set<Character> characterSet = new HashSet<>();

        for (var i = 0; i < length; i++) {
            if (!characterSet.contains(value.charAt(i))) {
                characterSet.add(value.charAt(i));
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean hasUniqueCharacters3(String value) {
        int length = value.length();
        if (length > 256) {
            return false;
        }

        for (int i = 0; i < length - 1; i++) {
            for (int j = i+1; j < length; j++) {
                if (value.charAt(i) == value.charAt(j)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean hasUniqueCharacters4(String value) {
        int length = value.length();
        if (length > 256) {
            return false;
        }
        BitSet bitSet = new BitSet(256);

        for (var i = 0; i < length; i++) {
            if (!bitSet.get(value.charAt(i))) {
                bitSet.set(value.charAt(i));
            } else {
                return false;
            }
        }

        return true;
    }
}
