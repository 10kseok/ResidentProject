package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.FamilyRelationShips;
import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.request.ResidentRequest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.DuplicateSerialNumberException;
import com.nhnacademy.resident_project.exception.ResidentNotFoundException;
import com.nhnacademy.resident_project.repository.FamilyRelationshipRepository;
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
    @Mock
    FamilyRelationshipRepository familyRelationshipRepository;
    @InjectMocks
    ResidentService residentService;

    ResidentRequest testResidentRequest;

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

        testResidentRequest = ResidentRequest.from(resident);
    }

    @Test
    @DisplayName("주민 등록 - 이미 있는 일련번호로 등록하는 경우")
    void save_1() {
        // given

        // when
        doReturn(testResidentRequest.toEntity()).when(residentRepository).saveAndFlush(testResidentRequest.toEntity());
        verify(residentRepository, atMostOnce()).saveAndFlush(testResidentRequest.toEntity());

        // then
        assertThat(residentService.save(testResidentRequest)).isEqualTo(testResidentRequest.getResidentSerialNumber());
        when(residentRepository.existsById(testResidentRequest.getResidentSerialNumber())).thenReturn(true);

        assertThatThrownBy(() -> residentService.save(testResidentRequest))
                .isInstanceOf(DuplicateSerialNumberException.class);
    }
    @Test
    @DisplayName("주민 등록 - 성공")
    void save_2() {
        // given

        // when
        doReturn(testResidentRequest.toEntity()).when(residentRepository).saveAndFlush(testResidentRequest.toEntity());
        verify(residentRepository, atMostOnce()).saveAndFlush(testResidentRequest.toEntity());
        when(residentRepository.findById(testResidentRequest.getResidentSerialNumber())).thenReturn(Optional.of(testResidentRequest.toEntity()));

        // then
        assertThat(residentService.save(testResidentRequest)).isEqualTo(testResidentRequest.getResidentSerialNumber());
        assertThat(residentService.findResidentById(testResidentRequest.getResidentSerialNumber())).isEqualTo(testResidentRequest.toEntity());
    }

    @Test
    @DisplayName("가족 관계 등록 - 없는 주민일련번호 등록")
    void save_3() {
        // given
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(1, 9999, "father");
        when(residentRepository.findById(any())).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> residentService.save(req))
                .isInstanceOf(ResidentNotFoundException.class);

    }

    @Test
    @DisplayName("가족 관계 등록 - 정상 등록")
    void save_4() {
        // given
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(1, 2, "father");
        when(residentRepository.findById(any())).thenReturn(any());

        // when
        boolean result = residentService.save(req);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주민정보 수정")
    void update_1() {
        // given
        ResidentRequest newResident = testResidentRequest;
        newResident.setName("test");
        doReturn(testResidentRequest.toEntity())
                .when(residentRepository).saveAndFlush(testResidentRequest.toEntity());
        doReturn(Optional.of(testResidentRequest.toEntity()))
                .when(residentRepository).findById(testResidentRequest.toEntity().getResidentSerialNumber());
        // when
        assertThat(residentService.update(newResident))
                .isEqualTo(newResident.getResidentSerialNumber());
        // then
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
        when(residentRepository.findById(testResidentRequest.getResidentSerialNumber()))
            .thenReturn(Optional.of(testResidentRequest.toEntity()));

        assertThat(residentService.findResidentById(1)).isEqualTo(testResidentRequest.toEntity());
    }

    @Test
    @DisplayName("가족 관계 삭제 - 성공")
    void delete_1() {
        // given
        FamilyRelationshipRequest request = new FamilyRelationshipRequest(1, 2, "child");

        // when
        boolean result = residentService.delete(request);

        // then
        assertThat(result).isTrue();
    }

}