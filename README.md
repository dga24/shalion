# School Management API

A REST API for managing schools and students built with Kotlin, Spring Boot, and PostgreSQL.

## Tech Stack

- **Kotlin** with Spring Boot 3.5.4
- **PostgreSQL** database
- **Docker** and Docker Compose
- **Gradle** build system
- **JPA/Hibernate** for data persistence

## Features

- **Schools Management**: Create, read, update, delete schools
- **Students Management**: Create, read, update, delete students
- **Search Functionality**: Search schools and students by name
- **Capacity Validation**: Schools have maximum capacity (50-2000 students)
- **Pagination**: All list endpoints support pagination
- **Error Handling**: Proper HTTP status codes and error messages

## Quick Start

### Prerequisites

- Docker and Docker Compose
- JDK 17+ (for local development)

### Running with Docker

1. Clone the repository:
```bash
git clone [your-repo-url]
cd shalion
```

2. Start the PostgreSQL database:
```bash
docker-compose up -d
```

3. Set environment variables:
```bash
cp example.env my.env
# Edit my.env with your database credentials
```

4. Build and run the application:
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`

### Environment Variables

Create a `my.env` file based on `example.env`:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/shalion
SPRING_DATASOURCE_USERNAME=shalion_user
SPRING_DATASOURCE_PASSWORD=shalion_pass
```

## API Endpoints

### Schools

- `POST /api/v1/school` - Create a new school
- `GET /api/v1/school/{id}` - Get school by ID
- `GET /api/v1/school/{id}/details` - Get school with students (paginated)
- `PUT /api/v1/school/{id}` - Update school
- `DELETE /api/v1/school/{id}` - Delete school
- `GET /api/v1/school?name={name}` - Search schools by name (paginated)

### Students

- `POST /api/v1/students` - Create a new student
- `GET /api/v1/students/{id}` - Get student by ID
- `PUT /api/v1/students/{id}` - Update student
- `DELETE /api/v1/students/{id}` - Delete student
- `GET /api/v1/students/search?schoolId={id}&name={name}` - Search students by name in school (paginated)

## Request/Response Examples

### Create School
```bash
POST /api/v1/school
Content-Type: application/json

{
  "name": "Springfield Elementary",
  "capacity": 500
}
```

### Create Student
```bash
POST /api/v1/students
Content-Type: application/json

{
  "name": "Bart Simpson",
  "schoolId": 1
}
```

### Update Student
```bash
PUT /api/v1/students/1
Content-Type: application/json

{
  "name": "Bartholomew Simpson",
  "schoolId": 2
}
```

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK` - Successful GET requests
- `201 Created` - Successful POST requests
- `204 No Content` - Successful PUT/DELETE requests
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate school name

## Database Schema

### Schools Table
- `id` (Primary Key)
- `name` (Unique, Not Null)
- `capacity` (50-2000)

### Students Table
- `id` (Primary Key)
- `name` (Not Null)
- `school_id` (Foreign Key, Not Null)

## Development

### Running Tests
```bash
./gradlew test
```

### Building the Project
```bash
./gradlew build
```

### Database Migration
The application uses `ddl-auto: validate` mode. Database schema is created via SQL scripts in `docker/init-db/`.

## Project Structure

```
src/
├── main/kotlin/org/dga/shalion/
│   ├── entities/           # JPA entities
│   ├── repositories/       # Spring Data repositories
│   ├── impl/
│   │   ├── rest/v1/       # REST controllers
│   │   └── service/       # Business logic
│   └── exceptions/        # Error handling
└── main/resources/
    └── application.yaml   # Configuration
```
