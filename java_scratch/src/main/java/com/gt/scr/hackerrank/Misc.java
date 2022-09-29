package com.gt.scr.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Misc {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int Q = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> allLines = new ArrayList<>();
        for (int i = 0; i < Q; i++) {
            allLines.add(bufferedReader.readLine().trim());
        }

        allLines.forEach(line -> {
            final String[] arr = line.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            int n = Integer.parseInt(arr[2]);

            System.out.println(calculate(a, b, n));
        });

        bufferedReader.close();
    }

    private static String calculate(int a, int b, int n) {
        long s = a, p = 1;
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < n; i++) {
            s += p * b;
            p = p * 2;
            stringBuilder.append(s).append(" ");
        }

        return stringBuilder.toString().trim();
    }
}
