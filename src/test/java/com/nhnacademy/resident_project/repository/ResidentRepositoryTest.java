package com.nhnacademy.resident_project.repository;


import com.nhnacademy.resident_project.RepositoryIntegrationTest;
import com.nhnacademy.resident_project.entity.Resident;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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

        assertThatThrownBy(resident::get)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No value present");
    }

    @Test
    @DisplayName("필수요소를 미충족한 주민정보 저장")
    void save_not_enough_resident() {
        Resident resident = new Resident();
        resident.setResidentSerialNumber(8);
        resident.setName("나테스");

        assertThatThrownBy(() -> residentRepository.saveAndFlush(resident))
                .hasCauseInstanceOf(ConstraintViolationException.class);
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

        residentRepository.saveAndFlush(resident);

        assertThat(residentRepository.findById(8)).isPresent();
    }

    @Test
    @DisplayName("주민이 갖고있는 모든 가족관계 조회")
    void find_family_relation() {
        residentRepository.findAll()
                .stream()
                .map(Resident::getBaseRelationships)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("페이징 조회")
    void pagination() {
        Pageable pageable = PageRequest.of(0, 20, Sort.Direction.ASC, "residentSerialNumber");
        Page<Resident> result = residentRepository.getResidentsBy(pageable);
        result.forEach((r) -> System.out.println(r.getResidentSerialNumber()));
        assertThat(result.getNumberOfElements()).isPositive();
    }

    @Test
    @DisplayName("증명서발급 조회")
    void find_certificate_issue() {
        residentRepository.findCertificateByResidentId(4)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("증명서발급 삽입")
    void insert_certificate_issue() {
        residentRepository.insertCertificate(8876543210987654L, 4, "출생신고서", LocalDate.now());

        residentRepository.findCertificateByResidentId(4)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("출생사망신고서 삽입")
    void insert_birth_death_report_resident() {
        residentRepository.insertBirthDeathReport("출생", 7, 5, LocalDate.of(2023,3, 17), "부", null, "nam@nhnad.co.kr", "010-1234-5678");
        residentRepository.insertBirthDeathReport("사망", 7, 5, LocalDate.of(2023,3, 17), null, "비동거친족", "nam@nhnad.co.kr", "010-1234-5678");


        assertThat(residentRepository.findBirthDeathReport("출생", 7, 5)).isPresent();
        assertThat(residentRepository.findBirthDeathReport("사망", 7, 5)).isPresent();
    }

    @Test
    @DisplayName("출생사망신고서 수정 - 신고날짜 변경")
    void update_birth_death_report_resident() {
        residentRepository.updateBirthDeathReport("출생", 7, 4, LocalDate.of(2023,3, 30), "부", null, "nam@nhnad.co.kr", "010-1234-5678");

        assertThat(residentRepository.findBirthDeathReport("출생", 7, 4).get().getBirthDeathReportDate())
                .isEqualTo(LocalDate.of(2023, 3, 30));
    }

    @Test
    @DisplayName("출생사망신고서 삭제")
    void delete_birth_death_report_resident() {
        residentRepository.deleteBirthDeathReport("출생", 7, 4);

        assertThat(residentRepository.findBirthDeathReport("출생", 7, 4))
                .isEmpty();
    }
}