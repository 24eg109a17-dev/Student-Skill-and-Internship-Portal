# Architecture

### Request flow
1. React page triggers an Axios request.
2. Axios attaches the JWT bearer token.
3. Spring Security validates the token and role.
4. Controller validates the request DTO.
5. Service applies business rules.
6. Repository uses JPA/Hibernate.
7. MySQL persists data.
8. DTO response is serialized as JSON.

### Why layers?
Controllers handle HTTP; services contain business rules; repositories handle persistence. This keeps responsibilities separated and makes testing easier.
