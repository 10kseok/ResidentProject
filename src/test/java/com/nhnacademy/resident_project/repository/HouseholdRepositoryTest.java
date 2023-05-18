package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.RepositoryIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryIntegrationTest
class HouseholdRepositoryTest {
    @Autowired
    HouseholdRepository householdRepository;

    @Test
    @DisplayName("삽입한 데이터 확인")
    void find_all_inserted_data() {
        // failed
        assertThat(householdRepository.findAll()).hasSize(1);
    }
}