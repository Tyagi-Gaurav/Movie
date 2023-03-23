package com.gt.scr.powerset;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Input: Takes a string as input upto 23 characters with 2GB heap size
 * Output: Prints all the power sets of the given string.
 */
public class PowerSet {
    public static void main(String[] args) {
        var input = "abcdefghijklmnopqrstuvwxyz";

        final long startTime = System.currentTimeMillis();
        try {
            System.out.println("Total free memory (In Mb): " + Runtime.getRuntime().freeMemory() / (1024 * 1024));
            final List<String> powerSet = computePowerSet(input, 0);
            System.out.println("Count of elements: " + powerSet.size());
            System.out.println(powerSet
                    .stream().map(member -> String.format("{%s}", member))
                    .collect(Collectors.joining(",")));
        } catch(OutOfMemoryError error) {
            System.err.println(error.getMessage());
            System.out.println("Total free memory (In Mb): " + Runtime.getRuntime().freeMemory() / (1024 * 1024));
        } finally {
            final long totalTime = System.currentTimeMillis() - startTime;
            if (totalTime < 1000)
                System.out.println("Total Time (in Milliseconds): " + totalTime);
            else
                System.out.println("Total Time (in Seconds): " + totalTime / 1000);
        }
    }

    private static List<String> computePowerSet(String input, int index) {
        if (index + 1 == input.length()) {
            return List.of("", String.valueOf(input.charAt(index)));
        }

        String currentElement = String.valueOf(input.charAt(index));
        final List<String> subPowerSet = computePowerSet(input, index + 1);
        return subPowerSet.stream()
                .flatMap(member -> Stream.of(member, currentElement + member))
                .toList();
    }
}