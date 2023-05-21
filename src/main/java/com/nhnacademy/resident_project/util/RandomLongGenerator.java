package com.nhnacademy.resident_project.util;

public class RandomLongGenerator {
    private RandomLongGenerator() {
        throw new IllegalStateException("Util class");
    }
    public static long nextLong(int numberOfDigit) {
        return (long) (Math.random() * Math.pow(10, numberOfDigit));
    }
}
