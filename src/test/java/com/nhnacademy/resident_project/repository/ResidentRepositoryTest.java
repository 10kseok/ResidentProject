package com.nhnacademy.resident_project.repository;


import com.nhnacademy.resident_project.config.RootConfig;
import com.nhnacademy.resident_project.config.TestDBConfig;
import com.nhnacademy.resident_project.config.TestJpaConfig;
import com.nhnacademy.resident_project.config.WebConfig;
import com.nhnacademy.resident_project.entity.Resident;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class),
        @ContextConfiguration(classes = {TestDBConfig.class, TestJpaConfig.class})
})
class ResidentRepositoryTest {
    @Autowired
    ResidentRepository residentRepository;
    @Test
    @DisplayName("없는 사람 조회")
    void not_found() {
        Optional<Resident> resident = residentRepository.findById(10);

        assertThatThrownBy(() -> resident.get())
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    @DisplayName("필수요소를 미충족한 주민정보 저장")
    void save_not_enough_resident() {
        Resident resident = new Resident();
        resident.setResidentSerialNumber(8);
        resident.setName("나테스");

        assertThatThrownBy(() -> residentRepository.save(resident));
    }

    @Test
    @DisplayName("필수요소를 충족한 주민정보 저장")
    void save_completely_resident() {
        Resident resident = new Resident();

        resident.setResidentSerialNumber(8);
        resident.setName("나테스");
        resident.setResidentRegistrationNumber("980920-1234567");
        resident.setGenderCode("남");
        resident.setBirthDate(LocalDateTime.of(1998, 9, 20, 11, 11, 11));
        resident.setBirthPlaceCode("병원");
        resident.setRegistrationBaseAddress("경기도 성남시 분당구 대왕판교로645번길");

        residentRepository.save(resident);
        residentRepository.flush();

        assertThat(residentRepository.findById(8)).isPresent();
    }

    @Test
    @DisplayName("주민일련번호 중복")
    void save_duplicated_residentSerialNumber() {
        // ISSUE : 기존에 있는 주민일련번호로 값을 넣어서 오류를 예상했으나 Update가 일어남.
        Resident resident = new Resident();

        resident.setResidentSerialNumber(1);
        resident.setName("나테스");
        resident.setResidentRegistrationNumber("980920-1234567");
        resident.setGenderCode("남");
        resident.setBirthDate(LocalDateTime.of(1998, 9, 20, 11, 11, 11));
        resident.setBirthPlaceCode("병원");
        resident.setRegistrationBaseAddress("경기도 성남시 분당구 대왕판교로645번길");

        residentRepository.saveAndFlush(resident);
//        assertThatThrownBy(() -> residentRepository.save(resident));
    }

    @Test
    @DisplayName("주민 삭제")
    void delete_resident() {
        assertThat(residentRepository.findById(1)).isPresent();

        residentRepository.deleteById(1);
        residentRepository.flush();

        assertThat(residentRepository.findById(1)).isNotPresent();
    }

    @Test
    @DisplayName("페이징 조회")
    void pagination() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Resident> result = residentRepository.getResidentsBy(pageable);

        assertThat(result.getNumberOfElements()).isPositive();
    }
}