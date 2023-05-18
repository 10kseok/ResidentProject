package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.RepositoryIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryIntegrationTest
class FamilyRelationshipRepositoryTest {
    @Autowired
    FamilyRelationshipRepository familyRelationshipRepository;
    @Test
    @DisplayName("모든 가족관계 조회")
    void findAll() {
        // NOTE : N+1 Problem
        familyRelationshipRepository.findAll();
    }
}