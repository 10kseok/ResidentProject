package com.nhnacademy.resident_project.repository;

import com.nhnacademy.resident_project.entity.BirthDeathReportResident;
import com.nhnacademy.resident_project.entity.CertificateIssue;
import com.nhnacademy.resident_project.entity.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    Page<Resident> getResidentsBy(Pageable pageable);
    @Query("select ci " +
            "FROM CertificateIssue ci " +
            "WHERE ci.issuedResident.residentSerialNumber = ?1 " +
            "ORDER BY ci.certificateConfirmationNumber ")
    Page<CertificateIssue> getCertificationsByResidentNumber(int serialNumber, Pageable pageable);

    @Query("SELECT ci FROM CertificateIssue ci WHERE ci.issuedResident.residentSerialNumber = ?1")
    List<CertificateIssue> findCertificateByResidentId(int number);

    @Modifying
    @Query(value = "INSERT INTO certificate_issue VALUES (?1, ?2, ?3, ?4) ", nativeQuery = true)
    void insertCertificate(Long id, int residentNumber, String typeCode, LocalDate issuedDate);

    @Modifying
    @Query(value = "UPDATE certificate_issue " +
            "SET certificate_type_code = ?2, certificate_issue_date = ?3 " +
            "WHERE certificate_confirmation_number = ?1 ", nativeQuery = true)
    void updateCertificate(Long id, String typeCode, LocalDate issuedDate);

    @Modifying
    @Query(value = "INSERT INTO birth_death_report_resident VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8) ", nativeQuery = true)
    void insertBirthDeathReport(String typeCode, int residentSerialNumber, int reportResidentSerialNumber,
                                LocalDate birthDeathReportDate, String birthReportQualificationsCode,
                                String deathReportQualificationsCode, String emailAddress, String phoneNumber);

    @Modifying
    @Query(value = "UPDATE birth_death_report_resident " +
            "SET birth_death_report_date = ?4, birth_report_qualifications_code = ?5, " +
            "death_report_qualifications_code = ?6, email_address = ?7,  phone_number = ?8 " +
            "WHERE birth_death_type_code = ?1 AND resident_serial_number = ?2 AND report_resident_serial_number = ?3 ",
            nativeQuery = true)
    void updateBirthDeathReport(String typeCode, int residentSerialNumber, int reportResidentSerialNumber,
                                LocalDate birthDeathReportDate, String birthReportQualificationsCode,
                                String deathReportQualificationsCode, String emailAddress, String phoneNumber);

    @Modifying
    @Query(value = "DELETE FROM birth_death_report_resident " +
            "WHERE birth_death_type_code = ?1 AND resident_serial_number = ?2 AND report_resident_serial_number = ?3",
            nativeQuery = true)
    void deleteBirthDeathReport(String typeCode, int residentSerialNumber, int reportResidentSerialNumber);

    @Query("SELECT br FROM BirthDeathReportResident br WHERE br.pk.birthDeathTypeCode = ?1 " +
            "AND br.pk.residentSerialNumber = ?2 " +
            "AND br.pk.reportResidentSerialNumber = ?3 ")
    Optional<BirthDeathReportResident> findBirthDeathReport(String typeCode, int residentSerialNumber, int reportResidentSerialNumber);
}

