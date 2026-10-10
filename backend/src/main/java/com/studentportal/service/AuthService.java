package com.studentportal.service;

import java.util.HashSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportal.dto.AuthDtos.*;
import com.studentportal.dto.AuthDtos.AuthResponse;
import com.studentportal.dto.AuthDtos.LoginRequest;
import com.studentportal.dto.AuthDtos.RegisterRequest;
import com.studentportal.entity.Role;
import com.studentportal.entity.Student;
import com.studentportal.entity.User;
import com.studentportal.exception.ApiException;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.UserRepository;
import com.studentportal.security.JwtService;

@Service
public class AuthService {

    private final UserRepository users;
    private final StudentRepository students;
    private final PasswordEncoder encoder;
    private final AuthenticationManager auth;
    private final JwtService jwt;

    public AuthService(
            UserRepository users,
            StudentRepository students,
            PasswordEncoder encoder,
            AuthenticationManager auth,
            JwtService jwt) {

        this.users = users;
        this.students = students;
        this.encoder = encoder;
        this.auth = auth;
        this.jwt = jwt;
    }

    // =========================================================
    // REGISTER
    // =========================================================

    @Transactional
    public AuthResponse register(RegisterRequest r) {

        // Check email
        if (r.email() == null || r.email().isBlank()) {
            throw new ApiException("Email is required");
        }

        // Check password
        if (r.password() == null || r.password().length() < 8) {
            throw new ApiException(
                    "Password must contain at least 8 characters"
            );
        }

        // Normalize email
        String email = r.email()
                .trim()
                .toLowerCase();

        // Check duplicate email
        if (users.existsByEmail(email)) {
            throw new ApiException(
                    "Email is already registered"
            );
        }

        // Create user
        User user = User.builder()
                .email(email)
                .password(encoder.encode(r.password()))
                .role(Role.STUDENT)
                .enabled(true)
                .build();

        // Save user first
        User savedUser = users.save(user);

        // Create student profile
        Student student = Student.builder()
                .user(savedUser)
                .fullName(r.fullName())
                .skills(new HashSet<>())
                .build();

        students.save(student);

        // Generate JWT token
        String token = jwt.generate(
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        return new AuthResponse(
                token,
                savedUser.getRole().name(),
                savedUser.getEmail()
        );
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @Transactional(readOnly=true)
    public AuthResponse login(LoginRequest r) {

        // Check email
        if (r.email() == null || r.email().isBlank()) {
            throw new ApiException("Email is required");
        }

        // Check password
        if (r.password() == null || r.password().isBlank()) {
            throw new ApiException("Password is required");
        }

        // Normalize email
        String email = r.email()
                .trim()
                .toLowerCase();

        try {

            // Authenticate email + password
            auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            r.password()
                    )
            );

        } catch (BadCredentialsException e) {

            throw new ApiException(
                    "Invalid email or password"
            );

        } catch (AuthenticationException e) {

            throw new ApiException(
                    "Authentication failed"
            );
        }

        // Find the user after successful authentication
        User user = users.findByEmail(email)
                .orElseThrow(() ->
                        new ApiException(
                                "User account not found"
                        )
                );

        // Check whether account is enabled
        if (!user.isEnabled()) {
            throw new ApiException(
                    "User account is disabled"
            );
        }

        // Generate JWT
        String token = jwt.generate(
                user.getEmail(),
                user.getRole().name()
        );

        // Return login response
        return new AuthResponse(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }
}