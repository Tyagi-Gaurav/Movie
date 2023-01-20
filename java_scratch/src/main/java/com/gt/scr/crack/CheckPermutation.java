package com.gt.scr.crack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
    If strings have different length then false.

    Approach 1: Keep a Map<Char, Int> and keep adding Int. If there is an odd value during the process or at the end then return false.
    Approach 2: Sort the strings and compare.
 */
public class CheckPermutation {
    public boolean check1(String original, String permutation) {
        if (original.length() != permutation.length()) {
            return false;
        }

        Map<Character, Integer> count = new HashMap<>();

        for (var i = 0; i < permutation.length(); i++) {
            count.putIfAbsent(permutation.charAt(i), 1);
            count.computeIfPresent(permutation.charAt(i), (character, integer) -> integer + 1);
        }

        for (var i = 0; i < original.length(); i++) {
            count.putIfAbsent(original.charAt(i), 1);
            count.computeIfPresent(original.charAt(i), (character, integer) -> integer + 1);
        }

        return count.values().stream()
                .anyMatch(x -> x % 2 != 0);
    }

    public boolean check2(String original, String permutation) {
        char[] originalArray = original.toCharArray();
        Arrays.sort(originalArray);
        char[] permutationArray = permutation.toCharArray();
        Arrays.sort(permutationArray);

        return String.valueOf(originalArray).equals(String.valueOf(permutationArray));
    }
}
