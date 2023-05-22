package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.entity.Household;
import com.nhnacademy.resident_project.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface HouseholdRepository extends JpaRepository<Household, Integer> {

    @Query("SELECT r FROM Resident r WHERE r.residentSerialNumber = ?1 ")
    Resident findResidentBySerialNumber(int residentSerialNumber);

    @Modifying
    @Query(value = "INSERT INTO household_movement_address " +
            "VALUES(?1, ?2, ?3, ?4) "
            , nativeQuery = true)
    void insertMovementAddress(LocalDate reportDate, int serialNumber, String newAddress, char lastAddressYN);
    @Modifying
    @Query(value = "UPDATE household_movement_address " +
            "SET house_movement_address = ?3, last_address_yn = ?4 " +
            "WHERE house_movement_report_date = ?1 AND household_serial_number = ?2 "
            , nativeQuery = true)
    void updateMovementAddress(LocalDate reportDate, int householdSerialNumber, String houseMovementAddress, char lastAddressYN);
    @Modifying
    @Query(value = "DELETE FROM household_movement_address " +
            "WHERE house_movement_report_date = ?1 AND household_serial_number = ?2 "
            , nativeQuery = true)
    void deleteMovement(LocalDate reportDate, int householdSerialNumber);
}
