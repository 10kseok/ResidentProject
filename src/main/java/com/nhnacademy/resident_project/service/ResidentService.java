package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.ResidentRequest;
import com.nhnacademy.resident_project.entity.Resident;
import com.nhnacademy.resident_project.exception.DuplicateSerialNumberException;
import com.nhnacademy.resident_project.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResidentService {
    private final ResidentRepository residentRepository;

    public int save(ResidentRequest residentRequest) {
        if (residentRepository.existsById(residentRequest.getResidentSerialNumber())) {
            throw new DuplicateSerialNumberException();
        }

        Resident resident = residentRequest.toResident();

        residentRepository.saveAndFlush(resident);
        return resident.getResidentSerialNumber();
    }

    public int update(ResidentRequest residentRequest) {
        residentRepository.saveAndFlush(residentRequest.toResident());
        return residentRequest.getResidentSerialNumber();
    }

    public Optional<Resident> findResidentById(Integer serialNumber) {
        return residentRepository.findById(serialNumber);
    }
}
