# School Management API

A REST API for managing schools and students built with Kotlin, Spring Boot, and PostgreSQL.

## Tech Stack

- **Kotlin** + Spring Boot
- **PostgreSQL** database
- **Docker** for containerization
- **Swagger UI** for API documentation

## Quick Start

## Environment Variables

The project uses a single `.env` file with different variables for different scenarios:

```bash
# For local development (app runs outside Docker)
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/shalion

# For Docker (app runs inside Docker)
DOCKER_DATASOURCE_URL=jdbc:postgresql://postgres:5432/shalion

# Shared credentials
POSTGRES_USER=shalion_user
POSTGRES_PASSWORD=shalion_pass
```

**Docker Compose automatically uses the correct variables from `.env`**

## API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## Docker Usage

### Standard Workflow
```bash
# Build and run everything
docker-compose up -d

# View application logs
docker-compose logs -f app

# Stop everything
docker-compose down
```

### Check the Postman Collection for testing
Import ```postman.json``` into Postman





