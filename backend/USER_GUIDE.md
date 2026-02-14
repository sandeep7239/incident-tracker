# Incident Tracker – Step-by-step user guide

## Where do login credentials come from?

**Credentials are not stored in the database.** They are defined in the backend code (in-memory) for this demo:

| Username | Password | Use this to |
|----------|----------|-------------|
| **admin** | **admin** | Sign in (default on login screen) |
| **user**  | **user**  | Sign in (alternative) |

- Stored in: `backend` → `UserDetailsConfig.java` (in-memory list).
- Not in PostgreSQL: there is no `users` table; only the **incidents** table is in the DB.
- To add real users in the future you would add a `users` table and load them in `UserDetailsService` from the DB.

---

## Step 0: Start the application

1. **Start the backend** (must be first):
   ```bash
   cd d:\incident-tracker\backend
   gradlew.bat bootRun
   ```
   Wait until you see something like: `Tomcat started on port(s): 8080`.

2. **Start the frontend**:
   ```bash
   cd d:\incident-tracker\frontend
   npm start
   ```
   Browser should open at `http://localhost:3000`.

---

## Step 1: Login screen

1. Open **http://localhost:3000** in the browser.
2. You are redirected to the **login** page (`/login`) if you are not logged in.
3. You see:
    - **Username** (default: `admin`)
    - **Password** (default: `admin`)
    - **Sign in** button
    - Hint: *Demo: admin / admin or user / user*

**What to do:**  
Leave **admin** / **admin** (or enter **user** / **user**) and click **Sign in**.

**What happens in the API:**
- The frontend calls: **`POST http://localhost:8080/api/auth/login`**
- Request body: `{ "username": "admin", "password": "admin" }`
- Backend checks the username/password against the in-memory users.
- If correct, backend returns a **JWT** and the frontend saves it (e.g. in browser storage) and redirects you to the home page.

---

## Step 2: After login – Incident list (home)

1. After a successful login you land on **/** (Incident list).
2. You see:
    - Top bar: **Incident Tracker**, **Incidents**, **Create**, **Logout**
    - Filters: Search, Service, Severity, Status
    - A **table** of incidents (paginated)
    - **Pagination** at the bottom

**What happens in the API:**
- The frontend calls: **`GET http://localhost:8080/api/incidents?page=0&size=10&sortBy=createdAt&sortDir=desc`**
- Request header: **`Authorization: Bearer <your-jwt-token>`**
- Backend validates the token; if valid, it returns the first page of incidents from the **database** (PostgreSQL).

So at this step you are:
- Using the **same credentials** (admin/user) that were checked at login (from in-memory config).
- Reading **incident data** from the **database** (not credentials).

---

## Step 3: Create a new incident

1. Click **Create** in the top bar.
2. Fill the form, e.g.:
    - **Title:** e.g. `Test incident`
    - **Service:** e.g. `payment-service`
    - **Severity:** e.g. `SEV2`
    - **Status:** e.g. `OPEN`
    - **Owner** (optional): e.g. `admin@zeotap.com`
    - **Summary** (optional): e.g. `Testing create flow`
3. Click **Create incident**.

**What happens in the API:**
- **`POST http://localhost:8080/api/incidents`**
- Headers: **`Authorization: Bearer <token>`**, **`Content-Type: application/json`**
- Body: the form data as JSON (title, service, severity, status, owner, summary).
- Backend validates the token and the body, then **saves the new incident in the database** and returns the created incident (with id, createdAt, etc.).
- The frontend then typically navigates to the **detail** page of that incident.

So here:
- **Credentials** are still only the in-memory admin/user; nothing new is saved in the DB for login.
- **Incident data** is saved in the **incidents** table in PostgreSQL.

---

## Step 4: View and update an incident

1. From the list, click **View** on a row (or open an incident from the create flow).
2. You see the **incident detail** page: title, service, severity, status, owner, summary, created/updated times.
3. You can change **Status** (e.g. OPEN → MITIGATED → RESOLVED) and click **Update status**.

**What happens in the API:**
- **Get one incident:** **`GET http://localhost:8080/api/incidents/<id>`** with **`Authorization: Bearer <token>`**. Data comes from the **database**.
- **Update status:** **`PATCH http://localhost:8080/api/incidents/<id>`** with **`Authorization: Bearer <token>`** and body e.g. `{ "status": "RESOLVED" }`. Backend updates that incident in the **database**.

Again: only **incident** data is read/updated in the DB; **credentials** are still the fixed in-memory users.

---

## Step 5: Logout

1. Click **Logout** in the top bar.
2. The stored JWT is cleared and you are sent back to **/login**.
3. Any further call to the incident APIs without a valid token will get **401** and the app will redirect you to login again.

---

## Summary table

| Step        | Screen / action   | API called                    | Credentials used      | Data in DB        |
|------------|-------------------|-------------------------------|------------------------|-------------------|
| 1 – Login  | Login form        | `POST /api/auth/login`        | admin/admin or user/user (in-memory) | No credentials saved |
| 2 – List   | Incident list     | `GET /api/incidents?...`      | JWT from login        | Read **incidents** |
| 3 – Create | Create form       | `POST /api/incidents`         | JWT                   | **Save** new incident |
| 4 – Detail | View / edit       | `GET` and `PATCH /api/incidents/:id` | JWT         | Read/update **incidents** |
| 5 – Logout | Logout button     | (no API; token removed)       | —                     | —                 |

**Credentials:**
- **Login:** use **admin** / **admin** or **user** / **user**.
- They are **not** saved in the database; they live only in the backend’s in-memory config.
- **Database** is used for **incidents** (list, create, get, update).

---

## Optional: Using Swagger to hit the APIs yourself

1. Open **http://localhost:8080/swagger-ui.html**.
2. **Get a token:**
    - Find **Authentication** → **POST /api/auth/login**.
    - Click **Try it out**, set body to `{ "username": "admin", "password": "admin" }`, click **Execute**.
    - From the response, copy the **`token`** value (the long JWT string).
3. **Authorize:**
    - Click the **Authorize** button (top right).
    - In the value field, paste **only the token** (no "Bearer ").
    - Click **Authorize**, then **Close**.
4. Now you can try:
    - **GET /api/incidents** (list),
    - **POST /api/incidents** (create),
    - **GET /api/incidents/{id}** (one incident),
    - **PATCH /api/incidents/{id}** (update).

Same credentials (**admin** / **admin** or **user** / **user**), same rule: credentials are in-memory; only incident data is in the database.
