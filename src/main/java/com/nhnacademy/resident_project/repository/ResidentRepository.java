package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.domain.FamilyRelationShips;
import com.nhnacademy.resident_project.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    List<FamilyRelationShips> findAllByResidentSerialNumber(Integer id);
    Page<Resident> getResidentsBy(Pageable pageable);
}
