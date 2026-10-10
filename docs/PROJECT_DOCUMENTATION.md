# Project Documentation
## Problem
Students often use scattered sources for profiles, skills and internship opportunities. This project centralizes those activities.

## Scope
Student authentication/profile/skills, internship discovery and applications, plus administrator management and basic statistics.

## Architecture
React UI → Axios → REST controllers → services → Spring Data repositories → Hibernate/JPA → MySQL.

## Core entities
User, Student, Skill, Internship, JobApplication.

## Security
BCrypt password hashing, JWT bearer authentication, role-based endpoint authorization, CORS and Bean Validation.

## Business rules
1. An application requires an existing student and internship.
2. Closed internships cannot receive applications.
3. Past deadlines cannot receive applications.
4. The `(student_id, internship_id)` pair is unique.
5. Admin APIs require `ROLE_ADMIN`.

## Full tree
```text
Student-Skill-and-Internship-Portal-Java-Full-Stack/
├── README.md
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/studentportal/
├── frontend/
│   ├── package.json
│   └── src/
├── database/
├── docs/
├── postman/
└── presentation-material/
```
