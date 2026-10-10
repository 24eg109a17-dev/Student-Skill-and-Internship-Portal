# Demo Script
1. Start MySQL.
2. Start backend: `cd backend && mvn spring-boot:run`.
3. Start frontend: `cd frontend && npm run dev`.
4. Login as `student1@studentportal.local / Student@123`.
5. Show dashboard.
6. Open Profile and edit a field.
7. Open Skills and add a skill.
8. Open Internships.
9. Search `Java` and choose a work mode.
10. Open internship details.
11. Apply.
12. Open My Applications and show `APPLIED`.
13. Logout.
14. Login as `admin@studentportal.local / Admin@123`.
15. Show admin dashboard/statistics.
16. Change an application to `SHORTLISTED` or `SELECTED`.
17. Logout and return to student account.
18. Show updated application status.

Narrate the architecture while demonstrating: React → Axios → Spring Boot REST → service → repository/JPA → MySQL.
