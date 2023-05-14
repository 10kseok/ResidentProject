package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.config.RootConfig;
import com.nhnacademy.resident_project.config.TestDBConfig;
import com.nhnacademy.resident_project.config.TestJpaConfig;
import com.nhnacademy.resident_project.config.WebConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class),
        @ContextConfiguration(classes = {TestDBConfig.class, TestJpaConfig.class})
})
class HouseholdRepositoryTest {
    @Autowired
    HouseholdRepository householdRepository;
    @Test
    @DisplayName("삽입한 데이터 확인")
    void find_all_inserted_data() {
        assertThat(householdRepository.findAll()).hasSize(1);
    }
}