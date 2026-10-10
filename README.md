# Student Skill and Internship Portal

A Java Full Stack B.Tech project using **React + Spring Boot + MySQL + REST APIs**. The source requirements specify React as the frontend, Spring Boot as the backend, MySQL as the database, and REST/JSON communication. 

## Features
- Student registration/login with BCrypt + JWT
- Student profile and completeness indicator
- Skill CRUD
- Internship browse/search/filter through backend
- Internship details and business-rule validation
- Apply once per internship; deadline/open checks
- Application history and status tracking
- Admin dashboard, student/internship/application management
- Global JSON exception handling
- SQL schema + fictional seed data
- Postman collection, tests, viva, presentation and deployment guides

## Architecture
React → Axios → REST API → Spring Boot Controller → Service → Spring Data JPA/Hibernate → MySQL

## Prerequisites
- JDK 21
- Maven 3.9+
- Node.js 20+
- MySQL 8+

## Database

The application expects a MySQL database named `student_portal`. Hibernate validates/updates the mapped schema during startup; the supplied SQL remains the recommended clean-install path.
Create the database:
```sql
CREATE DATABASE student_portal;
```
Then run `database/schema.sql`, followed by `database/data.sql` if you want the sample dataset.

> `data.sql` contains BCrypt hashes generated for this project. The application also creates a default admin and sample internships on startup when `app.seed-data=true`.

## Backend setup
Copy `backend/.env.example` values into your shell/environment or edit `backend/src/main/resources/application.properties` for local development.

```bash
cd backend
mvn clean test
mvn clean package
mvn spring-boot:run
```
Backend: http://localhost:8080

## Frontend setup
```bash
cd frontend
npm install
npm run dev
```
Frontend: http://localhost:5173

## Demo credentials
Seeded accounts:
- Admin: `admin@studentportal.local` / `Admin@123`
- Student: `student1@studentportal.local` / `Student@123`

These are fictional demo credentials. Change them before any real deployment.

## Important API groups
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET/PUT /api/students/profile`
- `GET/POST/PUT/DELETE /api/students/skills`
- `GET /api/internships`
- `GET /api/internships/{id}`
- `POST /api/applications`
- `GET /api/applications/my`
- `GET /api/applications/{id}`
- Admin endpoints under `/api/admin/**`

## Testing
Backend unit tests use JUnit 5 and Mockito. Frontend production build is checked with `npm run build`.

## Security
Passwords are never stored in plaintext. JWTs are used for API authentication. Never commit real secrets. See `.env.example` and `docs/GITHUB_GUIDE.md`.

## Development notes
- The account email is intentionally read-only in the profile because it is the JWT identity. Change it through a dedicated account-management flow rather than invalidating an active token.
- Internship listing APIs return only OPEN internships; CLOSED internships remain visible in the admin area.

## Known limitations
- Resume is represented as a URL rather than binary file storage.
- No email service, company account, AI matching, cloud deployment or Docker is implemented.
- Seed data is for demonstration only.
- Production deployment still requires managed MySQL, secret management, HTTPS and operational configuration.

## Project folders
See the full tree at the end of `docs/PROJECT_DOCUMENTATION.md`.
