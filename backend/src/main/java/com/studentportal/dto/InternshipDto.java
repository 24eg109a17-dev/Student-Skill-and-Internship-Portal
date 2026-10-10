package com.studentportal.dto;
import com.studentportal.entity.*; import jakarta.validation.constraints.*; import java.math.*;
import java.time.*;
public class InternshipDto {
 public record Request(@NotBlank String companyName,@NotBlank String title,@NotBlank String description,@NotBlank String requiredSkills,@NotBlank String location,
 @NotNull WorkMode workMode,@NotBlank String duration,@DecimalMin(value="0.0",inclusive=true) BigDecimal stipend,@NotNull LocalDate startDate,@NotNull LocalDate applicationDeadline,
 @NotBlank String eligibility,@NotNull @Min(1) Integer openings,InternshipStatus status){}
 public record Response(Long id,String companyName,String title,String description,String requiredSkills,String location,WorkMode workMode,String duration,BigDecimal stipend,LocalDate startDate,LocalDate applicationDeadline,String eligibility,Integer openings,InternshipStatus status){}
}
