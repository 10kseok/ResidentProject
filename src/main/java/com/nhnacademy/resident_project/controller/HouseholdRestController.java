package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.domain.request.HouseholdMovementAddressRequest;
import com.nhnacademy.resident_project.domain.request.HouseholdRequest;
import com.nhnacademy.resident_project.exception.HouseholdNotSetException;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/household")
public class HouseholdRestController {

    private final HouseholdService householdService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseholdRequest register(@Valid @RequestBody HouseholdRequest request,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        householdService.save(request);
        return request;
    }

    @DeleteMapping("/{householdSerialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, LocalDateTime> register(@PathVariable Integer householdSerialNumber) {
        householdService.delete(householdSerialNumber);
        return Map.of("deletedAt", LocalDateTime.now());
    }

    // 세대 전입 주소
    @PostMapping("/{householdSerialNumber}/movement")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> registerMovement(@PathVariable Integer householdSerialNumber,
                                                 @Valid @RequestBody HouseholdMovementAddressRequest request,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        request.setHouseholdSerialNumber(householdSerialNumber);
        householdService.save(request);
        return Map.of("createdHouseholdSerialNumber", householdSerialNumber);
    }

    @PutMapping("/{householdSerialNumber}/movement/{reportDate}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> updateMovement(@PathVariable Integer householdSerialNumber,
                                               @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") LocalDate reportDate,
                                               @Valid @RequestBody HouseholdMovementAddressRequest request,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        request.setHouseholdSerialNumber(householdSerialNumber);
        request.setReportDate(reportDate);
        householdService.update(request);
        return Map.of("updatedHouseholdSerialNumber", householdSerialNumber);
    }

    @DeleteMapping("/{householdSerialNumber}/movement/{reportDate}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> deleteMovement(@PathVariable Integer householdSerialNumber,
                                        @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") LocalDate reportDate) {
        if (householdSerialNumber <= 0) {
            throw new HouseholdNotSetException(householdSerialNumber);
        }
        if (reportDate.isAfter(LocalDate.now())) {
            throw new DateTimeException("시각을 확인해주세요");
        }
        HouseholdMovementAddressRequest request = new HouseholdMovementAddressRequest();
        request.setHouseholdSerialNumber(householdSerialNumber);
        request.setReportDate(reportDate);
        householdService.delete(request);
        return Map.of("deletedHouseholdSerialNumber", householdSerialNumber);
    }
}
