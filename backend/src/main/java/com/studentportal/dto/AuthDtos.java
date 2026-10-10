package com.studentportal.dto;
import jakarta.validation.constraints.*;
public class AuthDtos {
 public record RegisterRequest(@NotBlank @Size(max=100) String fullName,@NotBlank @Email @Size(max=120) String email,@NotBlank @Size(min=8,max=72) String password){}
 public record LoginRequest(@NotBlank @Email @Size(max=120) String email,@NotBlank String password){}
 public record AuthResponse(String token,String role,String email){}
}
