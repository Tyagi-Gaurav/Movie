package com.gt.scr.hackerrank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EOF {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final List<String> lines = new ArrayList<>();

        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }

        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("%d %s%n", i+1, lines.get(i));
        }
    }
}
