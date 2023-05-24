package com.nhnacademy.resident_project.service;

import com.nhnacademy.resident_project.domain.request.HouseholdMovementAddressRequest;
import com.nhnacademy.resident_project.domain.request.HouseholdRequest;
import com.nhnacademy.resident_project.entity.Household;
import com.nhnacademy.resident_project.exception.HouseholdNotSetException;
import com.nhnacademy.resident_project.repository.HouseholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HouseholdService {
    private final HouseholdRepository householdRepository;
    @Transactional
    public int save(HouseholdRequest request) {
        Household household = request.toEntity(householdRepository.findResidentBySerialNumber(request.getResidentSerialNumber()));
        householdRepository.saveAndFlush(household);
        return request.getHouseholdSerialNumber();
    }

    public int delete(Integer householdSerialNumber) {
        householdRepository.deleteById(householdSerialNumber);
        return householdSerialNumber;
    }

    @Transactional
    public int save(HouseholdMovementAddressRequest request) {
        if (request.getHouseholdSerialNumber() <= 0) {
            throw new HouseholdNotSetException(request.getHouseholdSerialNumber());
        }
        householdRepository.insertMovementAddress(request.getReportDate(),
                request.getHouseholdSerialNumber(),
                request.getHouseMovementAddress(),
                request.getLastAddressYN());
        return request.getHouseholdSerialNumber();
    }

    @Transactional
    public int update(HouseholdMovementAddressRequest request) {
        if (request.getHouseholdSerialNumber() <= 0) {
            throw new HouseholdNotSetException(request.getHouseholdSerialNumber());
        }
        householdRepository.updateMovementAddress(request.getReportDate(),
                request.getHouseholdSerialNumber(),
                request.getHouseMovementAddress(),
                request.getLastAddressYN());
        return request.getHouseholdSerialNumber();
    }

    @Transactional
    public int delete(HouseholdMovementAddressRequest request) {
        householdRepository.deleteMovement(request.getReportDate(),
                request.getHouseholdSerialNumber());
        return request.getHouseholdSerialNumber();
    }

    public boolean existBy(int houseSerialNumber) {
        return householdRepository.existsById(houseSerialNumber);
    }
}
