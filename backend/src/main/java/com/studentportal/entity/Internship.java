package com.studentportal.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="internships")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Internship {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false, length=150) private String companyName;
 @Column(nullable=false, length=180) private String title;
 @Column(nullable=false, columnDefinition="TEXT") private String description;
 @Column(nullable=false, length=500) private String requiredSkills;
 @Column(nullable=false, length=120) private String location;
 @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private WorkMode workMode;
 @Column(nullable=false, length=80) private String duration;
 @Column(precision=12, scale=2) private BigDecimal stipend;
 @Column(nullable=false) private LocalDate startDate;
 @Column(nullable=false) private LocalDate applicationDeadline;
 @Column(nullable=false, length=500) private String eligibility;
 @Column(nullable=false) private Integer openings;
 @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private InternshipStatus status;
}
