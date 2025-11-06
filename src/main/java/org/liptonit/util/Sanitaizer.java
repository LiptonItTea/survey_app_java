package org.liptonit.util;

public class Sanitaizer {
    public static String convertCamelCaseToSnakeRegex(String input) {
        return input
                .replaceAll("([A-Z])(?=[A-Z])", "$1_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}
