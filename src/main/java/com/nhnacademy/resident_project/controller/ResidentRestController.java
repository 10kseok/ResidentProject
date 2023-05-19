package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.domain.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.ResidentRequest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.IllegalResidentAccessException;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentRestController {
    // TODO: Exception 처리 Advice 필요
    private final ResidentService residentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> register(@Valid @RequestBody ResidentRequest residentRequest,
                                         BindingResult bindingResult) {
        // validation
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        // save
        int serialNumber = residentService.save(residentRequest);

        return Map.of("residentSerialNumber", serialNumber);
    }

    @PutMapping("/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> update(@PathVariable Integer serialNumber, @Valid @RequestBody ResidentRequest residentRequest,
                                       BindingResult bindingResult) {
        // validation
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        if (serialNumber != residentRequest.getResidentSerialNumber()) {
            throw new IllegalResidentAccessException();
        }
        // update
        residentService.update(residentRequest);

        return Map.of("residentSerialNumber", serialNumber);
    }

    @GetMapping("/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResidentRequest getResidentBy(@PathVariable Integer serialNumber) {
        Resident resident = residentService.findResidentById(serialNumber);
        return ResidentRequest.from(resident);
    }

    // 가족관계
    @PostMapping("/{serialNumber}/relationship")
    public Map<String, Object> registerRelationship(@PathVariable Integer serialNumber,
                                                    @RequestBody FamilyRelationshipRequest familyRelationshipRequest) {
        // validation

        // post
        // residentService.save(resident);
//        FamilyRelationship familyRelationship = new FamilyRelationship();
        return Map.of("", "");
    }

    @PutMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<Void> update(@PathVariable Integer serialNumber,
                                @PathVariable Integer familySerialNumber) {
        // validation

        // update
        // residentService.update(resident);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{serialNumber}/relationship/{familySerialNumber}")
    public ResponseEntity<Void> delete(@PathVariable Integer serialNumber,
                                @PathVariable Integer familySerialNumber) {
        // validation

        // delete
        // residentService.delete(resident);
        return ResponseEntity.ok()
                .build();
    }

    // 출생 신고
    @PostMapping("/{serialNumber}/birth")
    public ResponseEntity<Void> registerBirth(@PathVariable Integer serialNumber) {
        // validation

        // post
        // residentService.save(resident);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{serialNumber}/birth/{targetSerialNumber}")
    public ResponseEntity<Void> updateBirthReport(@PathVariable Integer serialNumber,
                                           @PathVariable Integer targetSerialNumber) {
        // validation

        // update
        // residentService.update(resident);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{serialNumber}/birth/{targetSerialNumber}")
    public ResponseEntity<Void> deleteBirthReport(@PathVariable Integer serialNumber,
                                           @PathVariable Integer targetSerialNumber) {
        // validation

        // delete
        // residentService.delete(resident);
        return ResponseEntity.ok()
                .build();
    }

    // 사망 신고
    @PostMapping("/{serialNumber}/death")
    public ResponseEntity<Void> registerDeath(@PathVariable Integer serialNumber) {
        // validation

        // post
        // residentService.save(resident);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{serialNumber}/death/{targetSerialNumber}")
    public ResponseEntity<Void> updateDeathReport(@PathVariable Integer serialNumber,
                                           @PathVariable Integer targetSerialNumber) {
        // validation

        // update
        // residentService.update(resident);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{serialNumber}/death/{targetSerialNumber}")
    public ResponseEntity<Void> deleteDeathReport(@PathVariable Integer serialNumber,
                                           @PathVariable Integer targetSerialNumber) {
        // validation

        // delete
        // residentService.delete(resident);
        return ResponseEntity.ok()
                .build();
    }

}
