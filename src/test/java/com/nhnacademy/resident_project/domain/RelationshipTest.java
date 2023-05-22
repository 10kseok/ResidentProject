package com.nhnacademy.resident_project.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationshipTest {
    @Test
    void matches() {
        Assertions.assertThat(Relationship.matches("자식")).isTrue();
        Assertions.assertThat(Relationship.matches("child")).isTrue();
        Assertions.assertThat(Relationship.matches("Child")).isTrue();
        Assertions.assertThat(Relationship.matches("CHILD")).isTrue();
        Assertions.assertThat(Relationship.matches("삼촌")).isFalse();
    }
}