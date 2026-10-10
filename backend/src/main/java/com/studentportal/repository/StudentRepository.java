package com.studentportal.repository;
import com.studentportal.entity.Student; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface StudentRepository extends JpaRepository<Student,Long>{ Optional<Student> findByUserEmail(String email); }
