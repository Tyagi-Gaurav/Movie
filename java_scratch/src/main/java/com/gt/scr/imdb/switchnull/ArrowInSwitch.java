package com.gt.scr.imdb.switchnull;

import static java.util.stream.IntStream.range;

public class ArrowInSwitch {
    public static void main(String[] args) {
        range(0, 4).forEach(ArrowInSwitch::arrows);
    }

    private static void arrows(int i) {
        switch(i) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            case 3 -> System.out.println("three");
            default -> System.out.println("default");
        }
    }
}
