package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, FamilyRelationship.PK> {
}
