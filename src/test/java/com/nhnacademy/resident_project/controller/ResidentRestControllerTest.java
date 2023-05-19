package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.domain.ResidentRequest;
import com.nhnacademy.resident_project.exception.IllegalResidentAccessException;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.ResidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static com.nhnacademy.resident_project.config.WebTestConfig.mappingJackson2HttpMessageConverter;
import static com.nhnacademy.resident_project.config.WebTestConfig.objectMapper;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResidentRestControllerTest {

    private MockMvc mockMvc;
    private ResidentService residentService;
    @BeforeEach
    void setup() {
        residentService = mock(ResidentService.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new ResidentRestController(residentService))
                .setMessageConverters(mappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    @DisplayName("주민 등록 - 필수조건 미충족")
    void register_1() throws Exception {
        ResidentRequest residentRequest = new ResidentRequest();
        residentRequest.setResidentSerialNumber(1);
        residentRequest.setName("failTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);


        assertThatThrownBy(() -> mockMvc.perform(requestBuilder))
                .hasCauseInstanceOf(ValidationFailedException.class);
    }
    @Test
    @DisplayName("주민 등록 - 성공")
    void register_2() throws Exception {
        ResidentRequest residentRequest = ResidentRequest.builder()
                .residentSerialNumber(8)
                .name("test")
                .residentRegistrationNumber("000920-1234567")
                .genderCode("남")
                .birthDate(LocalDateTime.now().minusYears(25))
                .birthPlaceCode("자택")
                .registrationBaseAddress("광주광역시 지산동")
                .build();

        when(residentService.save(any())).thenReturn(8);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.residentSerialNumber").value(8));
    }

    @Test
    @DisplayName("주민 정보 업데이트 - 필수조건 미충족")
    void update_1() throws Exception {
        ResidentRequest residentRequest = new ResidentRequest();
        residentRequest.setResidentSerialNumber(1);
        residentRequest.setName("failTest");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/1")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        assertThatThrownBy(() -> mockMvc.perform(requestBuilder))
                .hasCauseInstanceOf(ValidationFailedException.class);
    }

    @Test
    @DisplayName("주민 정보 업데이트 - 요청한 주민과 다른 주민정보 변경")
    void update_2() throws Exception {
        ResidentRequest residentRequest = ResidentRequest.builder()
                .residentSerialNumber(1)
                .name("changedName")
                .residentRegistrationNumber("000920-1234567")
                .genderCode("남")
                .birthDate(LocalDateTime.now().minusYears(25))
                .birthPlaceCode("자택")
                .registrationBaseAddress("광주광역시 지산동")
                .build();

        when(residentService.update(any())).thenReturn(1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/2")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);


        assertThatThrownBy(() -> mockMvc.perform(requestBuilder))
                .hasCauseInstanceOf(IllegalResidentAccessException.class)
                .hasMessageContaining("주민일련번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("주민 정보 업데이트 - 성공")
    void update_3() throws Exception {
        ResidentRequest residentRequest = ResidentRequest.builder()
                .residentSerialNumber(1)
                .name("changedName")
                .residentRegistrationNumber("000920-1234567")
                .genderCode("남")
                .birthDate(LocalDateTime.now().minusYears(25))
                .birthPlaceCode("자택")
                .registrationBaseAddress("광주광역시 지산동")
                .build();

        when(residentService.update(any())).thenReturn(1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/1")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residentSerialNumber").value(1));
    }

    @Test
    void registerRelationship() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void registerBirth() {
    }

    @Test
    void updateBirthReport() {
    }

    @Test
    void deleteBirthReport() {
    }

    @Test
    void registerDeath() {
    }

    @Test
    void updateDeathReport() {
    }

    @Test
    void deleteDeathReport() {
    }
}