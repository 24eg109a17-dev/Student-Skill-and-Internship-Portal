package com.studentportal.config;

import com.studentportal.entity.Internship;
import com.studentportal.entity.InternshipStatus;
import com.studentportal.entity.Role;
import com.studentportal.entity.Skill;
import com.studentportal.entity.Student;
import com.studentportal.entity.User;
import com.studentportal.entity.WorkMode;
import com.studentportal.repository.InternshipRepository;
import com.studentportal.repository.SkillRepository;
import com.studentportal.repository.StudentRepository;
import com.studentportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(
            UserRepository users,
            StudentRepository students,
            SkillRepository skills,
            InternshipRepository internships,
            PasswordEncoder enc,
            @Value("${app.seed-data:true}") boolean seed
    ) {
        return args -> {

            if (!seed) {
                return;
            }

            users.findByEmail("admin@studentportal.local").orElseGet(() -> users.save(User.builder().email("admin@studentportal.local").password(enc.encode("Admin@123")).role(Role.ADMIN).enabled(true).build()));

            String[] names = {
                    "Aarav Sharma",
                    "Diya Rao",
                    "Kabir Mehta",
                    "Meera Nair",
                    "Rohan Varma"
            };

            for (int x = 1; x <= 5; x++) {

                String studentEmail = "student" + x + "@studentportal.local";
                String studentName = names[x - 1];
                User user = users.findByEmail(studentEmail).orElseGet(() -> users.save(User.builder().email(studentEmail).password(enc.encode("Student@123")).role(Role.STUDENT).enabled(true).build()));

                Student student = students.findByUserEmail(studentEmail).orElseGet(() -> students.save(Student.builder().user(user).fullName(studentName).college("Anurag University").degree("B.Tech").branch("CSE").yearOfStudy(2).cgpa(new BigDecimal("8.2")).location("Hyderabad").build()));

            }

            String[] skillNames = {
                    "Java",
                    "Python",
                    "C",
                    "JavaScript",
                    "React",
                    "Spring Boot",
                    "MySQL",
                    "Cybersecurity",
                    "Networking",
                    "Git",
                    "Docker"
            };

            for (String name : skillNames) {
                if (skills.findByNameIgnoreCase(name).isEmpty()) skills.save(Skill.builder().name(name).build());
            }

            String[][] data = {
                    {
                            "TechNova Labs",
                            "Java Backend Intern",
                            "Build REST services with Java and Spring Boot.",
                            "Java, Spring Boot, MySQL",
                            "Hyderabad",
                            "HYBRID",
                            "3 Months",
                            "12000",
                            "Java/OOP basics"
                    },
                    {
                            "DataForge",
                            "React Developer Intern",
                            "Build responsive React interfaces.",
                            "React, JavaScript, Git",
                            "Remote",
                            "REMOTE",
                            "2 Months",
                            "10000",
                            "React fundamentals"
                    },
                    {
                            "CloudAxis",
                            "Full Stack Intern",
                            "Work across React, Java and relational databases.",
                            "React, Java, Spring Boot, MySQL",
                            "Bengaluru",
                            "ONSITE",
                            "6 Months",
                            "18000",
                            "B.Tech CSE/IT"
                    },
                    {
                            "SecureNet",
                            "Cybersecurity Intern",
                            "Assist with security testing and documentation.",
                            "Cybersecurity, Linux, Networking",
                            "Hyderabad",
                            "HYBRID",
                            "3 Months",
                            "9000",
                            "Networking fundamentals"
                    },
                    {
                            "GreenByte",
                            "Software Engineering Intern",
                            "Contribute to a student-facing product.",
                            "Java, Git, MySQL",
                            "Pune",
                            "REMOTE",
                            "4 Months",
                            "15000",
                            "Programming fundamentals"
                    },
                    {
                            "FinCore",
                            "Java Developer Intern",
                            "Develop transactional backend modules.",
                            "Java, Spring Boot, SQL",
                            "Mumbai",
                            "ONSITE",
                            "6 Months",
                            "20000",
                            "Java and SQL"
                    },
                    {
                            "EduWorks",
                            "Web Developer Intern",
                            "Create accessible web experiences.",
                            "HTML, CSS, JavaScript, React",
                            "Remote",
                            "REMOTE",
                            "3 Months",
                            "11000",
                            "Frontend fundamentals"
                    },
                    {
                            "LogiStack",
                            "Platform Engineering Intern",
                            "Support APIs and developer tooling.",
                            "Java, Docker, Git",
                            "Chennai",
                            "HYBRID",
                            "4 Months",
                            "14000",
                            "Java and Git"
                    }
            };

            if (internships.count() > 0) return;

            for (String[] d : data) {

                internships.save(
                        Internship.builder()
                                .companyName(d[0])
                                .title(d[1])
                                .description(d[2])
                                .requiredSkills(d[3])
                                .location(d[4])
                                .workMode(WorkMode.valueOf(d[5]))
                                .duration(d[6])
                                .stipend(new BigDecimal(d[7]))
                                .startDate(LocalDate.now().plusDays(30))
                                .applicationDeadline(LocalDate.now().plusDays(60))
                                .eligibility(d[8])
                                .openings(3)
                                .status(InternshipStatus.OPEN)
                                .build()
                );
            }
        };
    }
}