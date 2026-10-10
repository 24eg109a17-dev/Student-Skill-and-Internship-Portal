package com.studentportal.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public class ProfileDto {
 public record Request(@NotBlank @Size(max=100) String fullName,@Email @NotBlank @Size(max=120) String email,@Pattern(regexp="^$|[0-9+() -]{7,20}$",message="Must contain 7-20 valid phone characters") String phone,
 String college,String degree,String branch,@Min(1) @Max(8) Integer yearOfStudy,@DecimalMin("0.0") @DecimalMax("10.0") BigDecimal cgpa,
 String location,@Pattern(regexp="^$|https?://.+",message="Must be a valid http(s) URL") String githubUrl,
 @Pattern(regexp="^$|https?://.+",message="Must be a valid http(s) URL") String linkedinUrl,
 @Pattern(regexp="^$|https?://.+",message="Must be a valid http(s) URL") String portfolioUrl,
 @Pattern(regexp="^$|https?://.+",message="Must be a valid http(s) URL") String resumeUrl){}
 public record Response(Long id,String fullName,String email,String phone,String college,String degree,String branch,Integer yearOfStudy,BigDecimal cgpa,String location,String githubUrl,String linkedinUrl,String portfolioUrl,String resumeUrl,int profileCompleteness){}
}
