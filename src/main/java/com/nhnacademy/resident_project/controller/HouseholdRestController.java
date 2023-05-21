package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.domain.request.HouseholdRequest;
import com.nhnacademy.resident_project.entity.Household;
import com.nhnacademy.resident_project.service.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/household")
public class HouseholdRestController {

    private final HouseholdService householdService;

    @PostMapping
    public ResponseEntity<Void> register(HouseholdRequest household) {
        // validation

        // save
        // residentService.save();
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{householdSerialNumber}")
    public ResponseEntity<Void> register(@PathVariable Integer householdSerialNumber) {
        // validation

        // save
        // residentService.save();
        return ResponseEntity.ok()
                .build();
    }

    // 세대 전입 주소
    @PostMapping("/{householdSerialNumber}/movement")
    public ResponseEntity<Void> registerMovement(@PathVariable Integer householdSerialNumber) {
        // validation

        // post
        // residentService.save(resident);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{householdSerialNumber}/movement/{reportDate}")
    public ResponseEntity<Void> updateMovement(@PathVariable Integer householdSerialNumber,
                                        @PathVariable @DateTimeFormat(pattern = "YYYYMMDD") LocalDate reportDate) {
        // validation

        // update
        // residentService.update(resident);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{householdSerialNumber}/movement/{reportDate}}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Integer householdSerialNumber,
                                        @PathVariable @DateTimeFormat(pattern = "YYYYMMDD") LocalDate reportDate) {
        // validation

        // delete
        // residentService.delete(resident);
        return ResponseEntity.ok()
                .build();
    }

}
