# API Documentation

Base URL: `http://localhost:8080/api`

| Method | Endpoint | Auth | Purpose |
|---|---|---|---|
| POST | /auth/register | Public | Register student |
| POST | /auth/login | Public | Login |
| GET/PUT | /students/profile | Student | Read/update profile |
| GET/POST/DELETE | /students/skills | Student | Manage skills |
| GET | /internships | Auth | Search/filter |
| GET | /internships/{id} | Auth | Details |
| POST | /applications | Student | Apply |
| GET | /applications/my | Student | History |
| GET | /applications/{id} | Student | Own application |
| GET | /admin/students | Admin | Students |
| GET | /admin/applications | Admin | Applications |
| GET | /admin/stats | Admin | Statistics |
| POST/PUT/DELETE | /admin/internships[/id] | Admin | Manage internships |
| PUT | /admin/applications/{id}/status | Admin | Update status |
