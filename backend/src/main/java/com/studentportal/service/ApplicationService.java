package com.studentportal.service;

import com.studentportal.dto.ApplicationDto.*;
import com.studentportal.entity.ApplicationStatus;
import com.studentportal.entity.Internship;
import com.studentportal.entity.InternshipStatus;
import com.studentportal.entity.JobApplication;
import com.studentportal.entity.Student;
import com.studentportal.exception.ApiException;
import com.studentportal.repository.InternshipRepository;
import com.studentportal.repository.JobApplicationRepository;
import com.studentportal.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ApplicationService {

    private final JobApplicationRepository apps;
    private final StudentRepository students;
    private final InternshipRepository internships;

    public ApplicationService(
            JobApplicationRepository apps,
            StudentRepository students,
            InternshipRepository internships) {

        this.apps = apps;
        this.students = students;
        this.internships = internships;
    }

    // =========================================================
    // CREATE / APPLY FOR INTERNSHIP
    // =========================================================
    @Transactional
    public Response create(String email, CreateRequest request) {

        Student student = students.findByUserEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("Student not found"));

        Internship internship = internships.findById(request.internshipId())
                .orElseThrow(() ->
                        new NoSuchElementException("Internship not found"));

        // Check internship status
        if (internship.getStatus() != InternshipStatus.OPEN) {
            throw new ApiException("Internship is closed");
        }

        // Check application deadline
        if (internship.getApplicationDeadline() != null
                && internship.getApplicationDeadline()
                        .isBefore(LocalDate.now())) {

            throw new ApiException(
                    "Application deadline has passed");
        }

        // Prevent duplicate application
        if (apps.existsByStudentIdAndInternshipId(
                student.getId(),
                internship.getId())) {

            throw new ApiException(
                    "Student has already applied for this internship");
        }

        // Create application
        JobApplication application = JobApplication.builder()
                .student(student)
                .internship(internship)
                .applicationDate(LocalDateTime.now())
                .resume(request.resume())
                .coverLetter(request.coverLetter())
                .status(ApplicationStatus.APPLIED)
                .build();

        JobApplication savedApplication =
                apps.save(application);

        return out(savedApplication);
    }

    // =========================================================
    // GET CURRENT STUDENT'S APPLICATIONS
    // =========================================================
    @Transactional
    public List<Response> mine(String email) {

        Student student = students.findByUserEmail(email)
                .orElseThrow(() ->
                        new NoSuchElementException("Student not found"));

        return apps.findMyApplications(student.getId())
                .stream()
                .map(this::out)
                .toList();
    }

    // =========================================================
    // GET ONE APPLICATION
    // =========================================================
    @Transactional
    public Response get(String email, Long id) {

        JobApplication application = apps.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Application not found"));

        if (application.getStudent() == null
                || application.getStudent().getUser() == null
                || application.getStudent().getUser().getEmail() == null) {

            throw new ApiException(
                    "Application student information is missing");
        }

        if (!application.getStudent()
                .getUser()
                .getEmail()
                .equalsIgnoreCase(email)) {

            throw new ApiException(
                    "You cannot access this application");
        }

        return out(application);
    }

    // =========================================================
    // GET ALL APPLICATIONS
    // =========================================================
    @Transactional
    public List<Response> all() {

        return apps.findAll()
                .stream()
                .map(this::out)
                .toList();
    }

    // =========================================================
    // UPDATE APPLICATION STATUS
    // =========================================================
    @Transactional
    public Response status(
            Long id,
            ApplicationStatus status) {

        JobApplication application = apps.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Application not found"));

        application.setStatus(status);

        JobApplication savedApplication =
                apps.save(application);

        return out(savedApplication);
    }

    // =========================================================
    // COUNT ALL APPLICATIONS
    // =========================================================
    @Transactional
    public long count() {
        return apps.count();
    }

    // =========================================================
    // COUNT SELECTED APPLICATIONS
    // =========================================================
    @Transactional
    public long selected() {
        return apps.countByStatus(
                ApplicationStatus.SELECTED);
    }

    // =========================================================
    // COUNT REJECTED APPLICATIONS
    // =========================================================
    @Transactional
    public long rejected() {
        return apps.countByStatus(
                ApplicationStatus.REJECTED);
    }

    // =========================================================
    // CONVERT JOB APPLICATION TO RESPONSE DTO
    // =========================================================
    private Response out(JobApplication application) {

        if (application == null) {
            throw new ApiException(
                    "Application data is missing");
        }

        if (application.getStudent() == null) {
            throw new ApiException(
                    "Student information is missing");
        }

        if (application.getStudent().getUser() == null) {
            throw new ApiException(
                    "User information is missing");
        }

        if (application.getInternship() == null) {
            throw new ApiException(
                    "Internship information is missing");
        }

        return new Response(
                application.getId(),

                application.getInternship().getId(),

                application.getInternship().getCompanyName(),

                application.getInternship().getTitle(),

                application.getApplicationDate(),

                application.getResume(),

                application.getCoverLetter(),

                application.getStatus(),

                application.getStudent().getFullName(),

                application.getStudent()
                        .getUser()
                        .getEmail()
        );
    }
}