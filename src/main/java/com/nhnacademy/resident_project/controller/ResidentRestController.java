package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.domain.Relationship;
import com.nhnacademy.resident_project.domain.reponse.ReportSuccessDTO;
import com.nhnacademy.resident_project.domain.reponse.ReportSuccessDTOImpl;
import com.nhnacademy.resident_project.domain.request.BirthDeathReportRequest;
import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.domain.request.ResidentRequest;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.IllegalResidentAccessException;
import com.nhnacademy.resident_project.exception.NoSuchRelationshipException;
import com.nhnacademy.resident_project.exception.ValidationFailedException;
import com.nhnacademy.resident_project.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentRestController {
    private final ResidentService residentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> register(@Valid @RequestBody ResidentRequest residentRequest,
                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        int serialNumber = residentService.save(residentRequest);
        return Map.of("residentSerialNumber", serialNumber);
    }

    @PutMapping("/{serialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> update(@PathVariable Integer serialNumber, @Valid @RequestBody ResidentRequest residentRequest,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        if (serialNumber != residentRequest.getResidentSerialNumber()) {
            throw new IllegalResidentAccessException();
        }
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
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, LocalDateTime> registerRelationship(@PathVariable("serialNumber") Integer baseSerialNumber,
                                                           @Valid @RequestBody FamilyRelationshipRequest request,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors() || baseSerialNumber <= 0
                || baseSerialNumber == request.getFamilyResidentSerialNumber()) {
            throw new ValidationFailedException(bindingResult);
        }
        if (!Relationship.matches(request.getFamilyRelationshipCode())) {
            throw new NoSuchRelationshipException();
        }

        request.setBaseResidentSerialNumber(baseSerialNumber);
        residentService.save(request);

        return Map.of("createdAt", LocalDateTime.now());
    }

    @PutMapping("/{serialNumber}/relationship/{familySerialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, LocalDateTime> update(@PathVariable("serialNumber") Integer baseSerialNumber,
                                             @PathVariable Integer familySerialNumber,
                                             @Valid @RequestBody FamilyRelationshipRequest request,
                                             BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors() || baseSerialNumber <= 0
                || baseSerialNumber == request.getFamilyResidentSerialNumber()) {
            throw new ValidationFailedException(bindingResult);
        }
        if (!Relationship.matches(request.getFamilyRelationshipCode())) {
            throw new NoSuchRelationshipException();
        }

        request.setBaseResidentSerialNumber(baseSerialNumber);
        request.setFamilyResidentSerialNumber(familySerialNumber);
        residentService.save(request);

        return Map.of("changedAt", LocalDateTime.now());
    }

    @DeleteMapping("/{serialNumber}/relationship/{familySerialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, LocalDateTime> delete(@PathVariable("serialNumber") Integer baseSerialNumber,
                                             @PathVariable Integer familySerialNumber) {
        if (baseSerialNumber <= 0 || familySerialNumber <= 0) {
            throw new IllegalResidentAccessException();
        }
        FamilyRelationshipRequest request = new FamilyRelationshipRequest();
        request.setBaseResidentSerialNumber(baseSerialNumber);
        request.setFamilyResidentSerialNumber(familySerialNumber);
        residentService.delete(request);
        return Map.of("deletedAt", LocalDateTime.now());
    }

    // 출생 신고, 사망 신고
    @PostMapping({"/{serialNumber}/birth", "/{serialNumber}/death"})
    @ResponseStatus(HttpStatus.CREATED)
    public ReportSuccessDTO registerBirth(@PathVariable Integer serialNumber,
                                          @Valid @RequestBody BirthDeathReportRequest request,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        request.setReportResidentSerialNumber(serialNumber);
        residentService.save(request);
        return new ReportSuccessDTOImpl().getFrom(request);
    }

    @PutMapping({"/{serialNumber}/birth/{targetSerialNumber}", "/{serialNumber}/death/{targetSerialNumber}"})
    @ResponseStatus(HttpStatus.OK)
    public ReportSuccessDTO updateBirthReport(@PathVariable Integer serialNumber,
                                                  @PathVariable Integer targetSerialNumber,
                                                  @Valid @RequestBody BirthDeathReportRequest request,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        request.setResidentSerialNumber(targetSerialNumber);
        request.setReportResidentSerialNumber(serialNumber);
        residentService.update(request);
        return new ReportSuccessDTOImpl().getFrom(request);
    }

    @DeleteMapping("/{serialNumber}/birth/{targetSerialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, LocalDateTime> deleteBirthReport(@PathVariable Integer serialNumber,
                                                  @PathVariable Integer targetSerialNumber) {
        BirthDeathReportRequest request = new BirthDeathReportRequest();
        request.setResidentSerialNumber(targetSerialNumber);
        request.setReportResidentSerialNumber(serialNumber);
        request.setTypeCode("출생");
        residentService.delete(request);
        return Map.of("deletedAt", LocalDateTime.now());
    }

    @DeleteMapping("/{serialNumber}/death/{targetSerialNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, LocalDateTime> deleteDeathReport(@PathVariable Integer serialNumber,
                                                        @PathVariable Integer targetSerialNumber) {
        BirthDeathReportRequest request = new BirthDeathReportRequest();
        request.setResidentSerialNumber(targetSerialNumber);
        request.setReportResidentSerialNumber(serialNumber);
        request.setTypeCode("사망");
        residentService.delete(request);
        return Map.of("deletedAt", LocalDateTime.now());
    }


}
