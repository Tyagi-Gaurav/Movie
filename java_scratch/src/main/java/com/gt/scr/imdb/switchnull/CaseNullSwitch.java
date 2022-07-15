package com.gt.scr.imdb.switchnull;

public class CaseNullSwitch {
    static void checkNull(String s) {
        switch (s) {
            case "XX" -> System.out.println("XX");
            //case null -> System.out.println("null");
            default -> System.out.println("default");
        }
    }

    public static void main(String[] args) {

    }
}
