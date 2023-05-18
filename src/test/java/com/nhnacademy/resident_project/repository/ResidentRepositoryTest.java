package com.nhnacademy.resident_project.repository;


import com.nhnacademy.resident_project.RepositoryIntegrationTest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RepositoryIntegrationTest
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
    @DisplayName("주민 삭제")
    void delete_resident() {
        // 참조 무결성 에러
        assertThat(residentRepository.findById(1)).isPresent();

        residentRepository.deleteById(1);
        residentRepository.flush();

        assertThat(residentRepository.findById(1)).isNotPresent();
    }

    @Test
    @DisplayName("주민이 갖고있는 모든 가족관계 조회")
    void find_family_relation() {
        residentRepository.findAll()
                .stream()
                .map(Resident::getFamilyRelationships)
                .flatMap(fr -> fr.stream())
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("페이징 조회")
    void pagination() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Resident> result = residentRepository.getResidentsBy(pageable);

        assertThat(result.getNumberOfElements()).isPositive();
    }
}