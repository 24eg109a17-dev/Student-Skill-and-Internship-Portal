package com.studentportal.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="applications", uniqueConstraints=@UniqueConstraint(name="uk_student_internship", columnNames={"student_id","internship_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JobApplication {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="student_id") private Student student;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="internship_id") private Internship internship;
 @Column(nullable=false) private LocalDateTime applicationDate;
 @Column(length=300) private String resume;
 @Column(columnDefinition="TEXT") private String coverLetter;
 @Enumerated(EnumType.STRING) @Column(nullable=false, length=20) private ApplicationStatus status;
}
