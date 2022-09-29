package com.gt.scr.hackerrank;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class FormatOutput {
    /*
    Formatting Rules:
    %[flags][width][.precision]conversion-character

    The [flags] define standard ways to modify the output and are most common for
    formatting integers and floating-point numbers.

    The [width] specifies the field width for outputting the argument.

    The [.precision] specifies the number of digits of precision when outputting
    floating-point values. Additionally, we can use it to define the length of a substring to extract from a String.
    */
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        var s1 = scanner.nextLine();
        var s2 = scanner.nextLine();
        var s3 = scanner.nextLine();

        System.out.println("================================");
        System.out.println(format(s1));
        System.out.println(format(s2));
        System.out.println(format(s3));
        System.out.println("================================");
    }

    private static String format(String s1) {
        final String[] s = s1.split(" ");
        String part1 = s[0];
        int part2 = Integer.parseInt(s[1]);

        final StringWriter out = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(out);
        printWriter.printf("%-15s%03d", part1, part2);
        return out.getBuffer().toString();
    }
}
