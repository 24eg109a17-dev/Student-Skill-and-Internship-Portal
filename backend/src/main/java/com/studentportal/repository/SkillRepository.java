package com.studentportal.repository;
import com.studentportal.entity.Skill; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SkillRepository extends JpaRepository<Skill,Long>{ Optional<Skill> findByNameIgnoreCase(String name); }
