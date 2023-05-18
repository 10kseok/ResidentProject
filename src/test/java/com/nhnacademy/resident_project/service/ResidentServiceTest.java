package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.ResidentRequest;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.DuplicateSerialNumberException;
import com.nhnacademy.resident_project.exception.ResidentNotFoundException;
import com.nhnacademy.resident_project.repository.ResidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResidentServiceTest {
    @Mock
    ResidentRepository residentRepository;
    @InjectMocks
    ResidentService residentService;

    Resident testResident;

    @BeforeEach
    void init() {
        Resident resident = new Resident();
        resident.setResidentSerialNumber(1);
        resident.setName("남길동");
        resident.setResidentRegistrationNumber("130914-1234561");
        resident.setGenderCode("남");
        resident.setBirthDate(LocalDateTime.of(1913, 9, 14, 7,22,0));
        resident.setBirthPlaceCode("자택");
        resident.setRegistrationBaseAddress("경기도 성남시 분당구 대왕판교로645번길");
        resident.setDeathDate(LocalDateTime.of(2021, 04, 29, 9,3,0));
        resident.setDeathPlaceCode("주택");
        resident.setDeathPlaceAddress("강원도 고성군 금강산로 290번길");

        testResident = resident;
    }

    @Test
    @DisplayName("이미있는 일련번호로 주민 등록")
    void save_1() {
        // given

        // when
        doReturn(testResident).when(residentRepository).saveAndFlush(testResident);
        verify(residentRepository, atMostOnce()).saveAndFlush(testResident);

        // then
        assertThat(residentService.save(ResidentRequest.from(testResident))).isEqualTo(testResident.getResidentSerialNumber());
        when(residentRepository.existsById(testResident.getResidentSerialNumber())).thenReturn(true);

        assertThatThrownBy(() -> residentService.save(ResidentRequest.from(testResident)))
                .isInstanceOf(DuplicateSerialNumberException.class);
    }
    @Test
    @DisplayName("주민 등록")
    void save_2() {
        // given

        // when
        doReturn(testResident).when(residentRepository).saveAndFlush(testResident);
        verify(residentRepository, atMostOnce()).saveAndFlush(testResident);
        when(residentRepository.findById(testResident.getResidentSerialNumber())).thenReturn(Optional.of(testResident));

        // then
        assertThat(residentService.save(ResidentRequest.from(testResident))).isEqualTo(testResident.getResidentSerialNumber());
        assertThat(residentService.findResidentById(testResident.getResidentSerialNumber())).isEqualTo(testResident);
    }
    @Test
    @DisplayName("주민정보 수정")
    void update_1() {
        when(residentRepository.saveAndFlush(testResident))
                .thenReturn(testResident);
        when(residentRepository.findById(testResident.getResidentSerialNumber()))
                .thenReturn(Optional.of(testResident));

        Resident newResident = testResident;
        newResident.setName("test");

        assertThat(residentService.update(ResidentRequest.from(newResident)))
                .isEqualTo(newResident.getResidentSerialNumber());
        assertThat(residentService.findResidentById(newResident.getResidentSerialNumber()).getName())
                .isEqualTo("test");
    }
    @Test
    @DisplayName("(없는 경우) Id 기준으로 주민 조회")
    void find_by_id_1() {
        when(residentRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> residentService.findResidentById(10))
                .isInstanceOf(ResidentNotFoundException.class);
    }

    @Test
    @DisplayName("Id 기준으로 주민 조회")
    void find_by_id_2() {
        when(residentRepository.findById(testResident.getResidentSerialNumber()))
            .thenReturn(Optional.of(testResident));

        assertThat(residentService.findResidentById(1)).isEqualTo(testResident);
    }
}