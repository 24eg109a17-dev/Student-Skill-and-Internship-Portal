CREATE DATABASE IF NOT EXISTS student_portal;
USE student_portal;
CREATE TABLE IF NOT EXISTS users (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 email VARCHAR(120) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 role VARCHAR(20) NOT NULL,
 enabled BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE IF NOT EXISTS students (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 user_id BIGINT NOT NULL UNIQUE,
 full_name VARCHAR(100) NOT NULL, phone VARCHAR(30), college VARCHAR(150), degree VARCHAR(100),
 branch VARCHAR(100), year_of_study INT, cgpa DECIMAL(4,2), location VARCHAR(100),
 github_url VARCHAR(300), linkedin_url VARCHAR(300), portfolio_url VARCHAR(300), resume_url VARCHAR(300),
 CONSTRAINT fk_student_user FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS skills (id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(80) NOT NULL UNIQUE);
CREATE TABLE IF NOT EXISTS student_skills (
 student_id BIGINT NOT NULL, skill_id BIGINT NOT NULL, PRIMARY KEY(student_id,skill_id),
 FOREIGN KEY(student_id) REFERENCES students(id) ON DELETE CASCADE,
 FOREIGN KEY(skill_id) REFERENCES skills(id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS internships (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, company_name VARCHAR(150) NOT NULL, title VARCHAR(180) NOT NULL,
 description TEXT NOT NULL, required_skills VARCHAR(500) NOT NULL, location VARCHAR(120) NOT NULL,
 work_mode VARCHAR(20) NOT NULL, duration VARCHAR(80) NOT NULL, stipend DECIMAL(12,2),
 start_date DATE NOT NULL, application_deadline DATE NOT NULL, eligibility VARCHAR(500) NOT NULL,
 openings INT NOT NULL, status VARCHAR(20) NOT NULL, INDEX idx_internship_status(status), INDEX idx_deadline(application_deadline)
);
CREATE TABLE IF NOT EXISTS applications (
 id BIGINT AUTO_INCREMENT PRIMARY KEY, student_id BIGINT NOT NULL, internship_id BIGINT NOT NULL,
 application_date DATETIME NOT NULL, resume VARCHAR(300), cover_letter TEXT, status VARCHAR(20) NOT NULL,
 CONSTRAINT uk_student_internship UNIQUE(student_id,internship_id),
 FOREIGN KEY(student_id) REFERENCES students(id), FOREIGN KEY(internship_id) REFERENCES internships(id),
 INDEX idx_application_status(status)
);
