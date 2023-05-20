package com.nhnacademy.resident_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.resident_project.advice.CommonRestControllerAdvice;
import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.request.ResidentRequest;
import com.nhnacademy.resident_project.exception.IllegalResidentAccessException;
import com.nhnacademy.resident_project.exception.MissingParameterException;
import com.nhnacademy.resident_project.exception.NoSuchRelationshipException;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.ResidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static com.nhnacademy.resident_project.config.WebTestConfig.mappingJackson2HttpMessageConverter;
import static com.nhnacademy.resident_project.config.WebTestConfig.objectMapper;
import static org.assertj.core.api.Assertions.assertThat;
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
                .setControllerAdvice(new CommonRestControllerAdvice())
                .addDispatcherServletCustomizer(dispatcherServlet -> dispatcherServlet.setThrowExceptionIfNoHandlerFound(true))
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

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(ValidationFailedException.class));
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

        when(residentService.save((ResidentRequest) any())).thenReturn(8);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.residentSerialNumber").value(8));
    }

    @ParameterizedTest
    @DisplayName("가족관계 등록 - 가족관계 잘못 입력")
    @ValueSource(strings = {"삼촌", "uncle", "papa", "123"})
    void register_3(String relation) throws Exception {
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(7, 8, relation);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents/8/relationship")
                .content(objectMapper().writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(NoSuchRelationshipException.class, ValidationFailedException.class));
    }
    @Test
    @DisplayName("가족관계 등록 - 같은 주민일련번호로 등록")
    void register_4() throws Exception {
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(7, 7, "father");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents/7/relationship")
                .content(objectMapper().writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(ValidationFailedException.class));
    }

    @Test
    @DisplayName("가족관계 등록 - 유효하지 않는 주민일련번호(음수) 등록")
    void register_5() throws Exception {
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(-1, -2, "father");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents/-1/relationship")
                .content(objectMapper().writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(ValidationFailedException.class));
    }

    @Test
    @DisplayName("가족관계 등록 - 정상 등록")
    void register_6() throws Exception {
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(7, 8, "father");

        when(residentService.save((FamilyRelationshipRequest) any())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/residents/8/relationship")
                .content(objectMapper().writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdAt").exists());
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

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(ValidationFailedException.class));
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

        when(residentService.update((ResidentRequest) any())).thenReturn(1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/2")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(IllegalResidentAccessException.class));
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

        when(residentService.update((ResidentRequest) any())).thenReturn(1);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/1")
                .content(objectMapper().writeValueAsString(residentRequest))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residentSerialNumber").value(1));
    }

    @Test
    @DisplayName("가족관계 수정 - 필수정보 누락")
    void update_4() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/residents/8/relationship/7")
                .content(objectMapper().writeValueAsString(new FamilyRelationshipRequest()))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(ValidationFailedException.class));
    }

    @Test
    @DisplayName("가족관계 삭제 - 유효하지 않은 일련번호1")
    void delete_1() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/residents/-1324/relationship/7");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(IllegalResidentAccessException.class));
    }

    @Test
    @DisplayName("가족관계 삭제 - 유효하지 않은 일련번호2")
    void delete_2() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/residents/1/relationship/-1324");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(IllegalResidentAccessException.class));
    }

    @Test
    @DisplayName("가족관계 삭제 - 정상삭제")
    void delete_3() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/residents/1/relationship/2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deletedAt").exists());
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