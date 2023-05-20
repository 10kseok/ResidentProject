package com.nhnacademy.resident_project;

import com.nhnacademy.resident_project.domain.Relationship;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestSomething {
    @Test
    void test1() {
        String temp = "true";
        Assertions.assertThat(Boolean.parseBoolean(temp)).isTrue();

        for(Relationship c : Relationship.values()) {
            System.out.println(c.getKorName());
            System.out.println(c.name());
        }

        String kor = "한국어";


    }
}
