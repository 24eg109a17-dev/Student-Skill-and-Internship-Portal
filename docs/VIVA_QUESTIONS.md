# Viva Questions and Answers

1. **What is the project?** A portal connecting students with internship opportunities.
2. **Why Spring Boot?** It simplifies production-style Java REST development.
3. **Why React?** Component-based UI and client-side routing.
4. **Why MySQL?** Structured relational data and constraints fit the domain.
5. **What is REST?** An HTTP-based resource API style.
6. **What is a controller?** The HTTP boundary of the backend.
7. **What is a service?** The business-logic layer.
8. **What is a repository?** Persistence abstraction.
9. **What is JPA?** Java persistence specification.
10. **What is Hibernate?** A JPA implementation.
11. **What is DTO?** Object used to carry API data.
12. **Why DTOs?** Avoid exposing persistence models directly.
13. **What is DI?** Dependencies are supplied by the framework.
14. **What is IoC?** Framework controls object creation/lifecycle.
15. **What is JWT?** Signed token carrying authentication claims.
16. **Why JWT?** Stateless API authentication.
17. **Where is JWT sent?** `Authorization: Bearer <token>`.
18. **How is password stored?** BCrypt hash.
19. **Why not plaintext?** Plaintext exposes credentials if storage leaks.
20. **What is CORS?** Browser cross-origin access policy.
21. **What is React state?** Data that changes UI rendering.
22. **What is useEffect?** Hook for side effects such as API calls.
23. **What is Axios?** HTTP client used by React.
24. **Why React Router?** Client-side page navigation.
25. **What is a protected route?** UI route requiring authentication/role.
26. **What is normalization?** Structuring tables to reduce unnecessary duplication.
27. **Why student_skills?** It represents many-to-many student/skill data.
28. **Why unique application constraint?** Prevent duplicate applications.
29. **What is a foreign key?** Referential link to another table.
30. **What is validation?** Rejecting invalid input before processing.
31. **Why backend validation?** Frontend can be bypassed.
32. **What happens on duplicate application?** Service rejects it and DB uniqueness also protects it.
33. **What happens after deadline?** Application service rejects the request.
34. **What statuses exist?** APPLIED, SHORTLISTED, SELECTED, REJECTED.
35. **What work modes exist?** REMOTE, HYBRID, ONSITE.
36. **What can admin do?** Manage internships, view applications/students, update statuses and see statistics.
37. **Why service layer?** Separation of concerns and testability.
38. **What is Hibernate lazy loading?** Related data is loaded when accessed.
39. **Why avoid direct entity JSON?** It can expose internals or cause relationship recursion.
40. **What is HTTP 400?** Client request validation/business input error.
41. **What is HTTP 401?** Authentication is missing/invalid.
42. **What is HTTP 403?** Authenticated user lacks permission.
43. **What is HTTP 404?** Requested resource is not found.
44. **What is HTTP 500?** Unexpected server-side error.
45. **What is a unit test?** Tests one unit of behavior in isolation.
46. **What is an integration test?** Tests multiple application components together.
47. **What is Postman?** API development/testing client.
48. **What is Maven?** Java build/dependency tool.
49. **Why `.env.example`?** Documents required configuration without committing secrets.
50. **How does React communicate with Spring Boot?** Axios sends JSON HTTP requests to REST endpoints.
51. **How is an internship application stored?** A row links a student and internship with date, resume, cover letter and status.
52. **Explain login in 2 minutes.** React posts credentials; Spring authenticates against the BCrypt hash; JWT is returned; Axios sends it on protected requests; Spring Security validates it.
53. **Explain architecture.** React UI → Axios → REST controller → service → repository/JPA → MySQL.
54. **Why use a relational database?** Students, skills, internships and applications have clear relationships and integrity constraints.
55. **How would you scale search?** Add indexes, pagination and more targeted queries.
56. **How would you add email?** Add a notification service and provider without coupling it to controllers.
57. **How would you deploy?** Build React, host it, deploy Spring Boot, connect to managed MySQL and configure secrets.
58. **What is a future AI feature?** Skill/internship matching, explicitly outside the current scope.
59. **What is a transaction?** Atomic unit of database work.
60. **What should never be committed?** Real passwords, JWT secrets, API keys or production DB credentials.

## 2-minute explanation
“This is a Java Full Stack Student Skill and Internship Portal. React provides the user interface, Axios communicates with Spring Boot REST APIs, Spring Security protects resources using BCrypt and JWT, and Spring Data JPA/Hibernate stores normalized data in MySQL. Students maintain profiles and skills, search internships, apply and track status. Administrators manage internships and applications and see basic statistics.”

## 5-minute explanation
Start with the problem, show the architecture, demonstrate registration/login, profile, skills, backend-filtered internship search, application business rules, then switch to admin to update status. Explain that the application has both frontend and backend validation and a database-level uniqueness constraint.
