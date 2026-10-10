package com.studentportal.repository;

import com.studentportal.entity.ApplicationStatus;
import com.studentportal.entity.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    boolean existsByStudentIdAndInternshipId(
            Long studentId,
            Long internshipId
    );

    @Query("""
        SELECT a
        FROM JobApplication a
        JOIN FETCH a.student s
        JOIN FETCH s.user u
        JOIN FETCH a.internship i
        WHERE s.id = :studentId
        ORDER BY a.applicationDate DESC
    """)
    List<JobApplication> findMyApplications(
            @Param("studentId") Long studentId
    );

    long countByStatus(ApplicationStatus status);

    long countByStudentId(Long studentId);
}