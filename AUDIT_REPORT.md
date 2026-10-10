# Student Skill & Internship Portal — Audit and Repair Report

## Scope

The project was audited across:
- React/Vite frontend
- Spring Boot 3.5.x backend
- Spring Security + JWT
- JPA/Hibernate entity/repository/service boundaries
- MySQL schema and seed data
- API contracts and frontend integration
- validation, authorization, error handling and state management
- project packaging and generated artifacts

## Defects identified and addressed

### Backend
1. Lazy JPA relations could be accessed after repository transactions ended, causing `LazyInitializationException` in profile skills, admin student lists and application lists. Service methods are now transaction-bound for relation access.
2. Public internship search did not restrict results to OPEN internships, so CLOSED records could appear in student browsing. Student search now returns OPEN internships only.
3. Admin needed a separate all-internships view after the OPEN-only student search rule. An admin `/api/admin/internships` endpoint was added.
4. Profile URL validation rejected normal empty optional fields because `@Pattern` does not treat `""` as valid. Optional phone/URL patterns now allow blank values while still validating populated values.
5. Profile email changes could invalidate the active JWT because the JWT subject is the account email. The profile now treats the account email as read-only instead of silently breaking the current session.
6. Registration DTO lacked complete Bean Validation annotations for name/email/password. Validation was strengthened.
7. Internship creation/update allowed inconsistent dates such as a past start date or deadline before the start date. Business validation now rejects these cases.
8. Negative stipends were not explicitly rejected. Non-negative validation was added.
9. Data seeding depended on `users.count() == 0`, making partial databases difficult to repair and potentially causing duplicate-seed conflicts. Seed records are now checked idempotently by identity/name where appropriate.
10. Unexpected exceptions printed stack traces directly with `printStackTrace()`. The handler now avoids direct stdout leakage.
11. Database integrity violations were not mapped to a clean API response. A 409 conflict handler was added.
12. `User.enabled` had a Lombok builder warning because the field initializer was ignored by `@Builder`; `@Builder.Default` was added.
13. Frontend/API behavior around HTTP 403 was unsafe: the Axios interceptor logged users out on both 401 and 403. It now treats 401 as session invalidation and leaves 403 as an authorization error.

### Frontend
14. Empty optional profile values could trigger backend validation errors; backend and frontend behavior is now aligned.
15. Profile email is rendered read-only to match the JWT identity rule.
16. Skill creation now rejects blank names before making an API call.
17. Admin internship numeric fields use number inputs with minimum constraints.
18. Admin close/status operations now handle API failures instead of creating unhandled promise rejections.
19. Frontend dependencies were changed from floating `latest` versions to pinned versions matching the lockfile for reproducible installation.
20. `node_modules` is intentionally excluded from the repaired ZIP because it is machine/platform-specific and the supplied archive contained unusable native frontend dependencies.

### Database / packaging
21. Generated `backend/target` artifacts were removed from the source ZIP.
22. Bundled `frontend/node_modules` was removed from the source ZIP.
23. SQL schema/data remains included as the clean-install database source.
24. `.env` files remain excluded; `.env.example` documents required environment variables.

## Verification performed

- Java backend main source was compiled successfully with Java 21 against the project's dependency set and Lombok annotation processing.
- Frontend dependency installation/build could not be completed inside this Linux audit environment because the original archive's native Vite/Rolldown dependency set was platform-specific/incomplete, and network installation timed out. The repaired ZIP therefore contains no copied `node_modules`; a clean `npm install` on the target Windows machine is required before `npm run build`.
- A real MySQL integration test was not possible in this environment because a user MySQL server/database was not available.

## Important runtime requirements

1. JDK 21
2. Maven 3.9+
3. Node.js 20+ (Node 22 LTS/current is suitable)
4. MySQL 8+
5. Create `student_portal` and run `database/schema.sql` for a clean database.
6. Configure `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, and `FRONTEND_ORIGIN` as needed.
7. Run `mvn clean test` and `mvn clean package` in `backend`.
8. Run `npm install`, `npm run build`, and `npm run dev` in `frontend`.

## Demo credentials

- Admin: `admin@studentportal.local` / `Admin@123`
- Student: `student1@studentportal.local` / `Student@123`

These are fictional development credentials and must be changed before real deployment.
