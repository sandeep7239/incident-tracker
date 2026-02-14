# Incident Tracker

Full-stack application for creating, browsing, and managing production incidents. Built as a complete, production-ready solution with **JWT authentication**, **server-side pagination**, **filtering**, **sorting**, and **test coverage** on both backend and frontend.

---

## Table of contents

- [Overview](#overview)
- [Tech stack](#tech-stack)
- [Functional requirements](#functional-requirements)
- [Quick start](#quick-start)
- [API overview](#api-overview)
- [Security (JWT)](#security-jwt)
- [Testing](#testing)
- [Screenshots](#screenshots)
- [Project structure](#project-structure)
- [Documentation](#documentation)
- [Design decisions](#design-decisions)
- [Improvements with more time](#improvements-with-more-time)

---

## Overview

| Component  | Description |
|------------|-------------|
| **Backend** | REST API with Spring Boot 3.2, Spring Security, JWT, JPA, PostgreSQL. Swagger UI for API exploration. |
| **Frontend** | React 19 SPA with React Router, Tailwind CSS, Axios. Login, incident list (paginated), create incident, detail with status update. |

**Repository layout:** `/backend` (Java/Gradle), `/frontend` (React/npm). Each has its own **README** with setup, run, and testing instructions.

---

## Tech stack

| Layer    | Backend | Frontend |
|----------|---------|----------|
| Runtime  | Java 21 | Node.js 18+ |
| Framework| Spring Boot 3.2, Spring Security | React 19 |
| Data     | Spring Data JPA, PostgreSQL | — |
| Auth     | JWT (jjwt) | Axios + localStorage |
| API docs | SpringDoc OpenAPI 3 (Swagger) | — |
| UI       | — | React Router 6, Tailwind CSS, Axios |
| Build    | Gradle 8.x | Create React App |
| Tests    | JUnit 5, Mockito, MockMvc, H2 | Jest, React Testing Library |

---

## Functional requirements

1. **Create incidents** with validation (title, service, severity, status; optional owner/summary).
2. **Fetch incidents** from PostgreSQL with server-side pagination.
3. **Paginated table** with loading states and server-side pagination.
4. **Search, filter, sort** – server-side: debounced search (title/service/owner), filter by service/severity/status, column sort.
5. **View incident details** and **update status** (detail page with status dropdown and “Update status”).
6. **Seed data** – on first run, ~200 incidents are seeded if the table is empty.
7. **Authentication** – JWT-based; login required; all incident APIs protected.

---

## Quick start

### Prerequisites

- **Java 21** (backend)
- **Node.js 18+** and npm (frontend)
- **PostgreSQL** running locally (default: `localhost:5432`, user `postgres`)

### 1. Database

No manual DB creation needed. The backend uses the default **`postgres`** database and creates the **incidents** table on startup. Adjust `backend/src/main/resources/application.properties` if your PostgreSQL user/password differ.

### 2. Backend

```bash
cd backend
./gradlew bootRun
```

Windows: `gradlew.bat bootRun`. Server: **http://localhost:8080**. Swagger UI: **http://localhost:8080/swagger-ui.html**.

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

App: **http://localhost:3000**. Log in with **admin** / **admin** (or **user** / **user**).

---

## API overview

Base URL: `http://localhost:8080/api`

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST   | `/api/auth/login` | Login; returns JWT | No |
| POST   | `/api/incidents` | Create incident | Bearer |
| GET    | `/api/incidents` | List (paginated, filter, sort) | Bearer |
| GET    | `/api/incidents/:id` | Get one incident | Bearer |
| PATCH  | `/api/incidents/:id` | Partial update (e.g. status) | Bearer |

**GET /api/incidents** query parameters: `page`, `size`, `search`, `service`, `severity`, `status`, `sortBy`, `sortDir`. See **backend/API_SAMPLES.md** for request/response examples.

**Swagger UI:** http://localhost:8080/swagger-ui.html — use **Authorize** and paste the JWT from login to call protected APIs.

---

## Security (JWT)

- **Login:** `POST /api/auth/login` with `{"username":"admin","password":"admin"}` returns a JWT.
- **Protected APIs:** All `/api/incidents/**` require header: `Authorization: Bearer <token>`.
- **Demo users:** `admin` / `admin`, `user` / `user` (in-memory; not stored in DB).
- **Production:** Set env `JWT_SECRET` and use a strong secret in production.

---

## Testing

### Backend

```bash
cd backend
./gradlew test
```

Tests use H2 in-memory and a test JWT secret. Coverage includes:

- **Unit:** IncidentServiceImpl, JwtUtil
- **Integration:** IncidentController (all endpoints with JWT), AuthController (login success/failure/validation)
- **Repository:** IncidentRepository (save, findById, findAllFiltered)

### Frontend

```bash
cd frontend
npm test
```

Runs Jest with React Testing Library. Covers App routing, Login (form, API call, error), ProtectedRoute, and Layout.

---

## Screenshots

Add your own screenshots for documentation or company submission. Create a folder (e.g. **`docs/`** or **`screenshots/`**) in the project root or in `backend/` / `frontend/`, place the images there, and reference them below.

### 1. Swagger UI

![Swagger UI](docs/swagger-ui.png)

*Swagger UI at http://localhost:8080/swagger-ui.html — Incident and Authentication endpoints. Use "Authorize" to add the JWT from login.*

**Suggested file:** `docs/swagger-ui.png` or `screenshots/swagger.png`

---

### 2. Database (PostgreSQL)

![Database](docs/database.png)

*PostgreSQL database (e.g. pgAdmin) showing the `incidents` table and sample data.*

**Suggested file:** `docs/database.png` or `screenshots/pgadmin.png`

---

### 3. Login screen (frontend)

![Login screen](docs/login-screen.png)

*Login page at http://localhost:3000/login. Demo credentials: admin / admin or user / user.*

**Suggested file:** `docs/login-screen.png` or `screenshots/login.png`

---

### 4. Incident list (frontend)

![Incident list](docs/incident-list.png)

*Incident list after login: paginated table with filters and search.*

**Suggested file:** `docs/incident-list.png` or `screenshots/list.png`

---

### 5. Create incident (frontend)

![Create incident](docs/create-incident.png)

*Create incident form with Title, Service, Severity, Status, Owner, Summary.*

**Suggested file:** `docs/create-incident.png` or `screenshots/create.png`

---

### 6. Incident detail (frontend)

![Incident detail](docs/incident-detail.png)

*Incident detail page with status update (OPEN / MITIGATED / RESOLVED).*

**Suggested file:** `docs/incident-detail.png` or `screenshots/detail.png`

---

**How to add screenshots:** Create the `docs/` folder (e.g. `mkdir docs`), save your images with the names above (or any name), and update the paths in this README if needed. The paths above use `docs/` at the project root.

---

## Project structure

```
incident-tracker/
├── backend/                 # Spring Boot API
│   ├── src/main/java/       # Controllers, services, entity, security, config, constants
│   ├── src/main/resources/  # application.properties
│   ├── src/test/            # Unit and integration tests
│   ├── build.gradle
│   ├── API_SAMPLES.md
│   └── README.md
├── frontend/                # React SPA
│   ├── src/
│   │   ├── api/             # auth.js, incidents.js
│   │   ├── components/      # Layout, ProtectedRoute, Table, Pagination, Filters
│   │   ├── pages/           # Login, IncidentList, IncidentDetail, CreateIncident
│   │   └── App.js, index.js, index.css
│   ├── package.json
│   └── README.md
├── docs/                    # (Create this) Add screenshots here
├── USER_GUIDE.md            # Step-by-step user guide
└── README.md                # This file
```

---

## Documentation

| Document | Description |
|----------|-------------|
| **README.md** (root) | This file — overview, quick start, screenshots. |
| **backend/README.md** | Backend setup, API, security, testing, structure, image placeholders. |
| **frontend/README.md** | Frontend setup, features, testing, structure, image placeholders. |
| **backend/API_SAMPLES.md** | Request/response examples for all APIs. |
| **USER_GUIDE.md** | Step-by-step guide: login, list, create, detail, logout; credentials and API flow. |

---

## Design decisions

- **Server-side pagination/filter/sort:** Keeps the API scalable and consistent with assignment requirements.
- **JWT in header:** Stateless auth; no server-side session store.
- **Debounced search:** Reduces API calls while the user types (~400 ms).
- **Constants in dedicated classes:** ApiConstants, QueryConstants, CommonConstants for maintainability.
- **Indexes on incidents:** On `service`, `severity`, `status`, `created_at` for faster queries.
- **CORS:** Backend allows `http://localhost:3000` for the React app.
- **In-memory users for demo:** Easy to replace with a `users` table and DB-backed UserDetailsService.

---

## Improvements with more time

- **Users in DB:** Persist users in PostgreSQL and load them in UserDetailsService.
- **Audit log:** Store who changed status and when.
- **Real-time:** WebSocket or SSE for live status updates.
- **E2E tests:** Playwright or Cypress for critical user flows.
- **Frontend:** Error boundary, loading skeletons, accessibility (ARIA, keyboard nav).
- **API:** Rate limiting, API versioning.

---

## License

Part of the Incident Tracker assignment. Use as required by your organization.
