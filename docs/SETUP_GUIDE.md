# Beginner Setup

1. Install JDK 21, Maven, Node.js 20+, and MySQL 8.
2. Create `student_portal`.
3. Run `database/schema.sql`.
4. Start MySQL.
5. Open `backend`, configure DB environment variables, run `mvn spring-boot:run`.
6. Open `frontend`, run `npm install` then `npm run dev`. Do not copy `node_modules` from another machine; native Vite/Rolldown packages are platform-specific.
7. Visit `http://localhost:5173`.
8. Login with the demo credentials in README.

If Maven is not installed, install Maven separately; the project includes `pom.xml` but not a Maven wrapper.
