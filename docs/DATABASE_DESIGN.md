# Database Design

`users` stores credentials and role. `students` extends the student account with academic/profile data. `skills` is a reusable skill catalog. `student_skills` is the normalized many-to-many join table. `internships` stores opportunities. `applications` connects students to internships and records application state.

Relationships:
- User 1—1 Student
- Student 1—N Applications
- Internship 1—N Applications
- Student N—N Skills

The unique application constraint prevents duplicate applications at database level as well as service level.
