package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {

    Page<Resident> getResidentsBy(Pageable pageable);
}
