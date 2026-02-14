# Incident Tracker – Frontend

React-based UI for the Incident Tracker application. **React 19**, **React Router 6**, **Tailwind CSS**, and **Axios**. Includes login, incident list (paginated, filterable, sortable), create incident, and incident detail with status update.

---

## Table of contents

- [Tech stack](#tech-stack)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Setup and run](#setup-and-run)
- [Testing](#testing)
- [Project structure](#project-structure)
- [Screenshots](#screenshots)
- [Environment](#environment)
- [Design decisions](#design-decisions)

---

## Tech stack

| Layer        | Technology                    |
|-------------|-------------------------------|
| UI          | React 19                      |
| Routing     | React Router 6                |
| Styling     | Tailwind CSS 3                |
| HTTP        | Axios                         |
| Build       | Create React App (react-scripts 5) |
| Testing     | Jest, React Testing Library   |

---

## Features

- **Login** – Username/password; stores JWT and sends it with every API request.
- **Protected routes** – Unauthenticated users are redirected to `/login`.
- **Incident list** – Server-side pagination, column sort, filters (service, severity, status), debounced search.
- **Create incident** – Form with validation; redirects to detail on success.
- **Incident detail** – View and update status (OPEN / MITIGATED / RESOLVED).
- **Logout** – Clears token and redirects to login.
- **Error handling** – 401 clears token and redirects to login; validation errors shown on forms.

---

## Prerequisites

- **Node.js** 18+ (LTS recommended)
- **npm** (or yarn)
- **Backend API** running at http://localhost:8080 (or set `REACT_APP_API_URL`)

---

## Setup and run

### 1. Install dependencies

```bash
cd frontend
npm install
```

### 2. Start the app

```bash
npm start
```

The app opens at **http://localhost:3000**. Ensure the backend is running so login and incident APIs work.

### 3. Build for production

```bash
npm run build
```

Output is in the `build/` folder (static files for deployment).

---

## Testing

```bash
npm test
```

Runs Jest in watch mode. Tests include:

- **App** – Routing, login page at `/login`, redirect when unauthenticated, main layout when authenticated.
- **Login** – Form rendering, default credentials, API call and token storage on success, error message on failure.
- **ProtectedRoute** – Renders children when token exists; does not render children when no token.
- **Layout** – Renders nav with title, Incidents, Create, and Logout.

---

## Project structure

```
frontend/
├── public/
├── src/
│   ├── api/           # auth.js, incidents.js (Axios client + interceptors)
│   ├── components/    # Layout, ProtectedRoute, IncidentTable, Pagination, Filters, LoadingSpinner
│   ├── pages/         # Login, IncidentList, IncidentDetail, CreateIncident
│   ├── App.js
│   ├── index.js
│   └── index.css      # Tailwind directives
├── package.json
├── tailwind.config.js
├── postcss.config.js
└── README.md          # This file
```

---

## Screenshots

Add your own screenshots below for documentation or demos. Suggested images:

### 1. Login screen

Place a screenshot of the login page (http://localhost:3000/login).

**File to attach:** e.g. `docs/login-screen.png` or `screenshots/login.png`

![Login screen](docs/login-screen.png)

*Caption: Login page with username and password fields. Demo credentials: admin / admin or user / user.*

---

### 2. Incident list (home)

Place a screenshot of the incident list after login (table, filters, pagination).

**File to attach:** e.g. `docs/incident-list.png` or `screenshots/list.png`

![Incident list](docs/incident-list.png)

*Caption: Paginated incident table with filters and search. Columns: Title, Service, Severity, Status, Owner, Created, Actions.*

---

### 3. Create incident form

Place a screenshot of the Create Incident page.

**File to attach:** e.g. `docs/create-incident.png` or `screenshots/create.png`

![Create incident](docs/create-incident.png)

*Caption: Create incident form with Title, Service, Severity, Status, Owner, and Summary.*

---

### 4. Incident detail / update status

Place a screenshot of the incident detail page with status dropdown and Update button.

**File to attach:** e.g. `docs/incident-detail.png` or `screenshots/detail.png`

![Incident detail](docs/incident-detail.png)

*Caption: Incident detail view with status update (OPEN / MITIGATED / RESOLVED).*

---

### 5. Mobile / responsive (optional)

Optional: screenshot of the same pages on a narrow viewport.

**File to attach:** e.g. `docs/mobile-view.png`

![Mobile view](docs/mobile-view.png)

*Caption: Responsive layout on small screens.*

---

## Environment

| Variable             | Description              | Default                    |
|----------------------|--------------------------|----------------------------|
| `REACT_APP_API_URL`  | Base URL for the backend | http://localhost:8080/api  |

Example for a different backend:

```bash
REACT_APP_API_URL=http://api.example.com/api npm start
```

---

## Design decisions

- **JWT in localStorage:** Simple and sufficient for this demo; for production consider httpOnly cookies or short-lived tokens with refresh.
- **Axios interceptors:** Add `Authorization: Bearer <token>` to every request; on 401 clear token and redirect to login.
- **Debounced search:** Reduces API calls while the user types (e.g. 400 ms).
- **Server-side pagination/filter/sort:** All list logic on the backend for consistency and scalability.
- **Tailwind CSS:** Utility-first styling and responsive layout without a heavy UI library.

---

## License

Part of the Incident Tracker assignment. Use as required by your organization.
