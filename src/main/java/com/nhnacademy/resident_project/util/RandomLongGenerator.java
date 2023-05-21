package com.nhnacademy.resident_project.util;

import java.security.SecureRandom;

public class RandomLongGenerator {
    private RandomLongGenerator() {
        throw new IllegalStateException("Util class");
    }
    public static long nextLong(int numberOfDigit) {
        SecureRandom random = new SecureRandom();
        return (long) (random.nextDouble() * Math.pow(10, numberOfDigit));
    }
}
