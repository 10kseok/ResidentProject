package com.nhnacademy.resident_project;

import com.nhnacademy.resident_project.domain.Relationship;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

class TestSomething {
    @Test
    void test1() {
        String temp = "true";
        Assertions.assertThat(Boolean.parseBoolean(temp)).isTrue();

        for(Relationship c : Relationship.values()) {
            System.out.println(c.getKorName());
            System.out.println(c.name());
        }
    }

    @Test
    void test2() {
        // ISSUE: 0.01 일시 Test Fail
        SecureRandom random = new SecureRandom();
        System.out.println("secure number" + (long) (random.nextDouble() * Math.pow(10, 16)));
        System.out.println((long) (Math.random() * Math.pow(10, 16)));

        Assertions.assertThat((long) (random.nextDouble() * Math.pow(10, 16)))
                .isGreaterThan((long) (1 * Math.pow(10, 15) - 1));
    }
}
