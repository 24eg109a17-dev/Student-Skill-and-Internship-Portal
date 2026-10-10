# Deployment

## Local
React runs on Vite (`localhost:5173`), Spring Boot on `localhost:8080`, MySQL locally.

## Production architecture
React → frontend hosting/CDN → Spring Boot server → managed MySQL.

Production work not configured here:
- HTTPS/domain
- managed MySQL
- secret manager
- database migrations
- monitoring
- production CORS domain
- file/object storage

Therefore this project does **not** claim a production deployment has been performed.
