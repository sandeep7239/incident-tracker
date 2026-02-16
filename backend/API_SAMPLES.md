# API request/response samples (realistic data)

Base URL: `https://incident-tracker-1.onrender.com/api`

**Authentication:** All incident APIs require a JWT. First call **POST /api/auth/login** to get a token, then send it in the header: `Authorization: Bearer <token>`.

---

## 0. POST /api/auth/login — Get JWT token (no auth required)

**Request:**
```json
{
  "username": "admin",
  "password": "admin"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "admin",
  "expiresInMs": 86400000
}
```

Use `token` in subsequent requests: `Authorization: Bearer <token>`.

**Demo users:** `admin` / `admin`, `user` / `user`

---

## 1. POST /api/incidents — Create incident

**Request (SEV1 – critical):**
```json
{
  "title": "Checkout API latency exceeding 2s P95",
  "service": "payment-service",
  "severity": "SEV1",
  "status": "OPEN",
  "owner": "sarah.chen@zeotap.com",
  "summary": "Customers reporting failed payments. P95 latency spiked from 400ms to 2.1s. Investigating DB and payment gateway."
}
```

**Request (SEV2 – high):**
```json
{
  "title": "Stripe webhook delivery failures",
  "service": "payment-service",
  "severity": "SEV2",
  "status": "MITIGATED",
  "owner": "mike.johnson@zeotap.com",
  "summary": "Webhook retries failing after 3 attempts. Signature validation and endpoint health checked. Retry queue processing."
}
```

**Request (minimal – only required fields):**
```json
{
  "title": "SSL certificate expiry in 14 days",
  "service": "api-gateway",
  "severity": "SEV3",
  "status": "OPEN"
}
```

**Response (201 Created):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Checkout API latency exceeding 2s P95",
  "service": "payment-service",
  "severity": "SEV1",
  "status": "OPEN",
  "owner": "sarah.chen@zeotap.com",
  "summary": "Customers reporting failed payments. P95 latency spiked from 400ms to 2.1s. Investigating DB and payment gateway.",
  "createdAt": "2025-02-14T10:30:00.123456789Z",
  "updatedAt": "2025-02-14T10:30:00.123456789Z"
}
```

---

## 2. GET /api/incidents — List (paginated, filter, sort)

**Example query:**
- `GET /api/incidents?page=0&size=10&sortBy=createdAt&sortDir=desc`

**With search and filters:**
- `GET /api/incidents?page=0&size=10&search=payment&service=payment-service&severity=SEV1&status=OPEN&sortBy=createdAt&sortDir=desc`

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
      "title": "Checkout API latency exceeding 2s P95 [1]",
      "service": "payment-service",
      "severity": "SEV1",
      "status": "OPEN",
      "owner": "sarah.chen@zeotap.com",
      "summary": "Customers reporting failed payments. Checkout P95 latency spiked from 400ms to 2.1s.",
      "createdAt": "2025-02-14T10:30:00.123456789Z",
      "updatedAt": "2025-02-14T10:30:00.123456789Z"
    },
    {
      "id": "b2c3d4e5-f6a7-8901-bcde-f12345678901",
      "title": "Stripe webhook delivery failures [2]",
      "service": "payment-service",
      "severity": "SEV2",
      "status": "MITIGATED",
      "owner": "mike.johnson@zeotap.com",
      "summary": "Stripe webhook retries failing after 3 attempts. Investigating signature validation.",
      "createdAt": "2025-02-14T09:15:22.987654321Z",
      "updatedAt": "2025-02-14T09:45:00.000000000Z"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 47,
  "totalPages": 5,
  "first": true,
  "last": false
}
```

---

## 3. GET /api/incidents/:id — Get one incident

**Example:** `GET /api/incidents/a1b2c3d4-e5f6-7890-abcd-ef1234567890`

**Response (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Checkout API latency exceeding 2s P95 [1]",
  "service": "payment-service",
  "severity": "SEV1",
  "status": "OPEN",
  "owner": "sarah.chen@zeotap.com",
  "summary": "Customers reporting failed payments. Checkout P95 latency spiked from 400ms to 2.1s. Suspect DB connection pool saturation. Scaling pool and adding read replicas.",
  "createdAt": "2025-02-14T10:30:00.123456789Z",
  "updatedAt": "2025-02-14T10:30:00.123456789Z"
}
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2025-02-14T10:35:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "Incident not found with id: a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

---

## 4. PATCH /api/incidents/:id — Update (partial)

**Example:** `PATCH /api/incidents/a1b2c3d4-e5f6-7890-abcd-ef1234567890`

**Request – update status only:**
```json
{
  "status": "MITIGATED"
}
```

**Request – update status and owner:**
```json
{
  "status": "RESOLVED",
  "owner": "priya.sharma@zeotap.com"
}
```

**Request – update summary:**
```json
{
  "summary": "Root cause: connection pool maxed. Increased pool size to 50 and added 2 read replicas. P95 back under 500ms."
}
```

**Request – multiple fields:**
```json
{
  "title": "Checkout API latency exceeding 2s P95 — RESOLVED",
  "status": "RESOLVED",
  "summary": "Resolved after pool scaling and replica add. Monitoring for 24h."
}
```

**Response (200 OK):**
```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "title": "Checkout API latency exceeding 2s P95 — RESOLVED",
  "service": "payment-service",
  "severity": "SEV1",
  "status": "RESOLVED",
  "owner": "sarah.chen@zeotap.com",
  "summary": "Resolved after pool scaling and replica add. Monitoring for 24h.",
  "createdAt": "2025-02-14T10:30:00.123456789Z",
  "updatedAt": "2025-02-14T11:00:15.555555555Z"
}
```

---

## Validation error example (POST with missing required field)

**Request:**
```json
{
  "title": "Some incident",
  "service": "payment-service"
}
```
*(missing severity and status)*

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-02-14T10:40:00.000Z",
  "status": 400,
  "error": "Validation Failed",
  "errors": {
    "severity": "Severity is required",
    "status": "Status is required"
  }
}
```

---

Use these samples in **Swagger UI** (`http://localhost:8080/swagger-ui.html`) or any REST client (Postman, curl).
