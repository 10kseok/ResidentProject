package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.RepositoryIntegrationTest;
import com.nhnacademy.resident_project.domain.request.FamilyRelationshipRequest;
import com.nhnacademy.resident_project.entity.FamilyRelationship;
import com.nhnacademy.resident_project.entity.Resident;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryIntegrationTest
class FamilyRelationshipRepositoryTest {
    @Autowired
    FamilyRelationshipRepository familyRelationshipRepository;
    @Autowired
    ResidentRepository residentRepository;
    @Test
    @DisplayName("모든 가족관계 조회")
    void findAll() {
        // NOTE : N+1 Problem
        Assertions.assertThat(familyRelationshipRepository.findAll()).hasSizeGreaterThan(1) ;
    }

    @Test
    @DisplayName("가족관계에서 주민 조회")
    void find_resident() {
        familyRelationshipRepository.findAll()
                .stream()
                .map(FamilyRelationship::getFamilyResident)
                .forEach(System.out::println);

        familyRelationshipRepository.findAll()
                .stream()
                .map(FamilyRelationship::getBaseResident)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("새로만든 가족관계 Entity에서 주민조회")
    void find_resident2() {
        FamilyRelationshipRequest req = new FamilyRelationshipRequest(1, 2, "부");
        FamilyRelationship fr = req.toEntity();
//        familyRelationshipRepository.saveAndFlush(fr);

        System.out.println(fr.getBaseResident());
        System.out.println(fr.getFamilyResident());

        FamilyRelationship fr2 = familyRelationshipRepository.findById(fr.getPk()).get();
        System.out.println(fr2.getBaseResident());
        System.out.println(fr2.getFamilyResident());
    }

    @Test
    @DisplayName("가족관계 등록 - 주민에서의 설정까지 (필요없음)")
    void find_resident3() {
        Resident base = residentRepository.findById(6).get();
        Resident family = residentRepository.findById(7).get();

        FamilyRelationship fr = new FamilyRelationship();
        fr.setPk(new FamilyRelationship.PK(7, 6));
        fr.setFamilyRelationshipCode("child");
        fr.setBaseResident(base);
        fr.setFamilyResident(family);

        base.getBaseRelationships().add(fr);
        family.getFamilyRelationships().add(fr);

        familyRelationshipRepository.saveAndFlush(fr);

        Assertions.assertThat(residentRepository.findById(6).get().getBaseRelationships()
                .stream()
                .filter(f -> f.getPk().equals(fr.getPk()))
                .count())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("가족관계 등록 - 주민에서의 설정없이")
    void find_resident4() {
        Resident base = residentRepository.findById(6).get();
        Resident family = residentRepository.findById(7).get();

        FamilyRelationship fr = new FamilyRelationship();
        fr.setPk(new FamilyRelationship.PK(7, 6));
        fr.setFamilyRelationshipCode("child");
        fr.setBaseResident(base);
        fr.setFamilyResident(family);

        familyRelationshipRepository.saveAndFlush(fr);

        Assertions.assertThat(residentRepository.findById(6).get().getBaseRelationships()
                        .stream()
                        .filter(f -> f.getPk().equals(fr.getPk()))
                        .count())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("가족관계 등록 - 주민일련번호로만")
    void find_resident5() {
        FamilyRelationship fr = new FamilyRelationship();
        fr.setPk(new FamilyRelationship.PK(7, 6));
        fr.setFamilyRelationshipCode("child");

        // JpaSystemException: attempted to assign id from null one-to-one property
        Assertions.assertThatThrownBy(() -> familyRelationshipRepository.saveAndFlush(fr));
    }
}