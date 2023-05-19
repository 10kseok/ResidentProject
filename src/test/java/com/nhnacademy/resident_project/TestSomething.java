package com.nhnacademy.resident_project;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TestSomething {
    @Test
    void test1() {
        String temp = "true";
        Assertions.assertThat(Boolean.parseBoolean(temp)).isTrue();
    }
}
