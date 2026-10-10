package com.studentportal.dto;
import com.studentportal.entity.ApplicationStatus; import jakarta.validation.constraints.*; import java.time.*;
public class ApplicationDto {
 public record CreateRequest(@NotNull Long internshipId,@Size(max=300) String resume,@Size(max=5000) String coverLetter){}
 public record Response(Long id,Long internshipId,String companyName,String internshipTitle,LocalDateTime applicationDate,String resume,String coverLetter,ApplicationStatus status,String studentName,String studentEmail){}
 public record StatusRequest(@NotNull ApplicationStatus status){}
}
