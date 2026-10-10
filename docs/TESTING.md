# Testing

## Automated
Run:
```bash
cd backend
mvn clean test
```
Tests cover duplicate-application rejection and registration behavior.

Frontend build:
```bash
cd frontend
npm install
npm run build
```

## Manual test cases
| ID | Test | Expected |
|---|---|---|
| TC001 | Valid registration | Account created |
| TC002 | Duplicate email | 400 |
| TC003 | Valid login | JWT returned |
| TC004 | Invalid password | Authentication fails |
| TC005 | Update profile | Data saved |
| TC006 | Add skill | Skill appears |
| TC007 | Browse internship | List loads |
| TC008 | Apply | APPLIED created |
| TC009 | Duplicate application | Rejected |
| TC010 | Expired internship | Rejected |
| TC011 | Admin changes status | New status visible |
| TC012 | Student calls admin endpoint | Forbidden |

## Limitations
Full browser/E2E testing and live MySQL integration are not executed in this package's generation environment.
