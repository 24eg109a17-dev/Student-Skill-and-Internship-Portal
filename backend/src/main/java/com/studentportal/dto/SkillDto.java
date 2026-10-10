package com.studentportal.dto;
import jakarta.validation.constraints.NotBlank;
public class SkillDto { public record Request(@NotBlank String name){} public record Response(Long id,String name){} }
