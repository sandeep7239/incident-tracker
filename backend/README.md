# Incident Tracker – Backend API

REST API for the Incident Tracker application. Built with **Java 21**, **Spring Boot 3.2**, **Spring Security**, **JWT**, **Spring Data JPA**, and **PostgreSQL**. Includes **Swagger (OpenAPI 3)** for interactive API documentation.

---

## Table of contents

- [Tech stack](#tech-stack)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup and run](#setup-and-run)
- [API overview](#api-overview)
- [Security and authentication](#security-and-authentication)
- [Testing](#testing)
- [Project structure](#project-structure)
- [Screenshots](#screenshots)
- [Configuration](#configuration)
- [Design decisions](#design-decisions)

---

## Tech stack

| Layer        | Technology                          |
|-------------|--------------------------------------|
| Runtime     | Java 21                              |
| Framework   | Spring Boot 3.2.5                    |
| Security    | Spring Security, JWT (jjwt 0.12)    |
| Data        | Spring Data JPA, Hibernate           |
| Database    | PostgreSQL                           |
| API docs    | SpringDoc OpenAPI 3 (Swagger UI)     |
| Build       | Gradle 8.x                           |
| Testing     | JUnit 5, Mockito, MockMvc, H2        |

---

## Features

- **CRUD for incidents** – Create, list (paginated), get by ID, partial update (PATCH).
- **Server-side filtering and sorting** – Search (title, service, owner), filter by service/severity/status, sort by any column.
- **JWT authentication** – Login endpoint; all incident APIs require `Authorization: Bearer <token>`.
- **Validation** – Bean Validation on request DTOs; global exception handler for 404 and validation errors.
- **Database seeding** – On first run, seeds ~200 sample incidents if the table is empty.
- **Swagger UI** – Interactive API docs at `/swagger-ui.html`.

---

## Prerequisites

- **Java 21**
- **PostgreSQL** (running locally; default database: `postgres` or configure `incident_tracker` in `application.properties`)
- **Gradle** (wrapper included: `gradlew` / `gradlew.bat`)

---

## Setup and run

### 1. Database

The application uses the default **`postgres`** database by default. Ensure PostgreSQL is running on `localhost:5432`. No manual DB creation is required; JPA creates the `incidents` table on startup.

To use a dedicated database, create it (e.g. `CREATE DATABASE incident_tracker;`) and set in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/incident_tracker
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 2. Build and run

```bash
cd backend
./gradlew build
./gradlew bootRun
```

Windows: `gradlew.bat build` and `gradlew.bat bootRun`.

Server starts at **http://localhost:8080**. Swagger UI: **http://localhost:8080/swagger-ui.html**.

---

## API overview

| Method | Endpoint                | Description                    | Auth   |
|--------|-------------------------|--------------------------------|--------|
| POST   | `/api/auth/login`       | Login; returns JWT             | No     |
| POST   | `/api/incidents`        | Create incident                | Bearer |
| GET    | `/api/incidents`        | List (paginated, filter, sort) | Bearer |
| GET    | `/api/incidents/{id}`   | Get one incident               | Bearer |
| PATCH  | `/api/incidents/{id}`   | Partial update                 | Bearer |

**List query parameters:** `page`, `size`, `search`, `service`, `severity`, `status`, `sortBy`, `sortDir`. See **API_SAMPLES.md** for examples.

---

## Security and authentication

- **Login:** `POST /api/auth/login` with `{"username":"admin","password":"admin"}` (or `user` / `user`). Returns a JWT.
- **Protected APIs:** Send header `Authorization: Bearer <token>` for all `/api/incidents/**` requests.
- **Demo users:** In-memory only: `admin` / `admin`, `user` / `user`.
- **JWT config:** `jwt.secret` and `jwt.expiration-ms` in `application.properties`; use env `JWT_SECRET` in production.

---

## Testing

```bash
./gradlew test
```

Tests use H2 in-memory and a test JWT secret. Coverage includes unit tests (IncidentServiceImpl, JwtUtil), integration tests (IncidentController, AuthController), and repository tests (IncidentRepository).

---

## Project structure

```
backend/
├── src/main/java/com/zeotap/backend/
│   ├── config/          # Security, Web, OpenAPI, UserDetails, DataSeeder
│   ├── constant/        # ApiConstants, QueryConstants, CommonConstants
│   ├── controller/      # IncidentController, AuthController
│   ├── dto/             # Request/Response DTOs
│   ├── entity/          # Incident, Severity, Status
│   ├── exception/       # ResourceNotFoundException, GlobalExceptionHandler
│   ├── repository/      # IncidentRepository
│   ├── security/        # JwtUtil, JwtAuthFilter
│   └── service/         # IncidentService, IncidentServiceImpl
├── src/main/resources/application.properties
├── src/test/            # Unit and integration tests
├── build.gradle
├── API_SAMPLES.md
└── README.md
```

---

## Screenshots

Add your own screenshots below. Create a `docs/` or `screenshots/` folder and place images there, then update the paths.

### 1. Swagger UI

![Swagger UI](docs/swagger-ui.png)

*Caption: Swagger UI at http://localhost:8080/swagger-ui.html. Use Authorize to add the JWT from login.*

### 2. Database schema

![Database schema](docs/database-schema.png)

*Caption: PostgreSQL schema (e.g. pgAdmin or ER diagram) for the incidents table.*

### 3. Login API response

![Login API response](docs/login-response.png)

*Caption: Example response from POST /api/auth/login with JWT token.*

### 4. Incident list API response

![Incident list API](docs/incident-list-api.png)

*Caption: Example response from GET /api/incidents (paginated list).*

---

## Configuration

| Property                 | Description                    | Default |
|--------------------------|--------------------------------|--------|
| `server.port`            | HTTP port                      | 8080   |
| `spring.datasource.url`  | JDBC URL                       | jdbc:postgresql://localhost:5432/postgres |
| `jwt.secret`             | JWT signing secret             | (set in properties; use JWT_SECRET in prod) |
| `jwt.expiration-ms`      | Token validity (ms)            | 86400000 |

---

## Design decisions

- **JWT in header:** Stateless auth; no server-side session store.
- **Constants in dedicated classes:** ApiConstants, QueryConstants, CommonConstants.
- **Server-side pagination/filter/sort:** Keeps API scalable.
- **In-memory users for demo:** Can be replaced with a users table and UserDetailsService from DB.
- **Indexes on incidents:** On service, severity, status, created_at for faster queries.
