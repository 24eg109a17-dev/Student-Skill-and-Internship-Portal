package com.studentportal.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.*;
@Entity @Table(name="students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne @JoinColumn(name="user_id", nullable=false, unique=true) private User user;
 @Column(nullable=false, length=100) private String fullName;
 @Column(length=30) private String phone;
 @Column(length=150) private String college;
 @Column(length=100) private String degree;
 @Column(length=100) private String branch;
 private Integer yearOfStudy;
 @Column(precision=4, scale=2) private BigDecimal cgpa;
 @Column(length=100) private String location;
 @Column(length=300) private String githubUrl;
 @Column(length=300) private String linkedinUrl;
 @Column(length=300) private String portfolioUrl;
 @Column(length=300) private String resumeUrl;
 @ManyToMany(fetch=FetchType.LAZY)
 @JoinTable(name="student_skills", joinColumns=@JoinColumn(name="student_id"), inverseJoinColumns=@JoinColumn(name="skill_id"))
 @Builder.Default private Set<Skill> skills = new HashSet<>();
}
