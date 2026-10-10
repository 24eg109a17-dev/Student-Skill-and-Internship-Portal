package com.studentportal.controller;
import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;

import com.studentportal.dto.AuthDtos.*;
import com.studentportal.dto.AuthDtos.AuthResponse;
import com.studentportal.dto.AuthDtos.LoginRequest;
import com.studentportal.dto.AuthDtos.RegisterRequest;
import com.studentportal.service.AuthService;

import jakarta.validation.Valid;
@RestController @RequestMapping("/api/auth")
public class AuthController {private final AuthService s; public AuthController(AuthService s){this.s=s;} @PostMapping("/register") public AuthResponse register(@Valid @RequestBody RegisterRequest r){return s.register(r);} @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){return s.login(r);}}
