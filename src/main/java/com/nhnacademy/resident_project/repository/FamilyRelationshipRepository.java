package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, FamilyRelationship.PK> {
    List<FamilyRelationship> findAllByFamilyResident(Resident resident);
    List<FamilyRelationship> findAllByBaseResident(Resident resident);
}
