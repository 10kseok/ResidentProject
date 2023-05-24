package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.advice.CommonRestControllerAdvice;
import com.nhnacademy.resident_project.domain.request.HouseholdRequest;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.HouseholdService;
import com.nhnacademy.resident_project.service.ResidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static com.nhnacademy.resident_project.config.WebTestConfig.mappingJackson2HttpMessageConverter;
import static com.nhnacademy.resident_project.config.WebTestConfig.objectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HouseholdRestControllerTest {
    HouseholdService householdService;
    private MockMvc mockMvc;
    @BeforeEach
    void setup() {
        householdService = mock(HouseholdService.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new HouseholdRestController(householdService))
                .setMessageConverters(mappingJackson2HttpMessageConverter())
                .setControllerAdvice(new CommonRestControllerAdvice())
                .addDispatcherServletCustomizer(dispatcherServlet -> dispatcherServlet.setThrowExceptionIfNoHandlerFound(true))
                .build();
    }


    @Test
    @DisplayName("세대 등록 - 필수조건 누락")
    void register_1() throws Exception {
        HouseholdRequest householdReq = new HouseholdRequest();
        householdReq.setCompositionReasonCode("세대 분리");

        RequestBuilder req = MockMvcRequestBuilders
                .post("/household")
                .content(objectMapper().writeValueAsString(householdReq))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(req)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(ValidationFailedException.class));
    }

    @Test
    @DisplayName("세대 등록 - 정상 등록")
    void register_2() throws Exception {
        HouseholdRequest householdReq = new HouseholdRequest();
        householdReq.setCompositionReasonCode("세대 분리");
        householdReq.setResidentSerialNumber(1);
        householdReq.setHouseholdSerialNumber(2);
        householdReq.setCurrentHouseMovementAddress("광주광역시");
        householdReq.setCompositionDate(LocalDate.now());

        RequestBuilder req = MockMvcRequestBuilders
                .post("/household")
                .content(objectMapper().writeValueAsString(householdReq))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(req)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.householdSerialNumber").value(2))
                .andExpect(jsonPath("$.residentSerialNumber").value(1))
                .andExpect(jsonPath("$.compositionDate").exists())
                .andExpect(jsonPath("$.currentHouseMovementAddress").value("광주광역시"))
                .andExpect(jsonPath("$.compositionReasonCode").value("세대 분리"));
    }

    @Test
    @DisplayName("세대 삭제 - 정상 삭제")
    void delete_1() throws Exception {
        RequestBuilder req = MockMvcRequestBuilders
                .delete("/household/1");

        mockMvc.perform(req)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deletedAt").exists());
    }

    @Test
    void registerMovement() {
    }

    @Test
    void updateMovement() {
    }

    @Test
    void deleteMovement() {
    }
}