package com.zeotap.backend.constant;

/**
 * Shared constants for entity, DTO, service, and exception handling.
 */
public final class CommonConstants {

    private CommonConstants() {
    }

    public static final String TABLE_INCIDENTS = "incidents";
    public static final String COLUMN_SERVICE = "service";
    public static final String COLUMN_SEVERITY = "severity";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    public static final String INDEX_INCIDENT_SERVICE = "idx_incident_service";
    public static final String INDEX_INCIDENT_SEVERITY = "idx_incident_severity";
    public static final String INDEX_INCIDENT_STATUS = "idx_incident_status";
    public static final String INDEX_INCIDENT_CREATED_AT = "idx_incident_created_at";

    public static final int LENGTH_TITLE = 255;
    public static final int LENGTH_SERVICE = 100;
    public static final int LENGTH_SEVERITY = 10;
    public static final int LENGTH_STATUS = 20;
    public static final int LENGTH_OWNER = 100;

    public static final String SORT_DIR_DESC = "desc";
    public static final String SORT_FIELD_CREATED_AT = "createdAt";

    public static final String VALIDATION_TITLE_REQUIRED = "Title is required";
    public static final String VALIDATION_SERVICE_REQUIRED = "Service is required";
    public static final String VALIDATION_SEVERITY_REQUIRED = "Severity is required";
    public static final String VALIDATION_STATUS_REQUIRED = "Status is required";

    public static final String SCHEMA_DESC_INCIDENT_RESPONSE = "Incident response";
    public static final String SCHEMA_DESC_CREATE_REQUEST = "Request body for creating a new incident";
    public static final String SCHEMA_DESC_UPDATE_REQUEST = "Request body for updating an incident (partial update)";
    public static final String SCHEMA_DESC_PAGE_RESPONSE = "Paginated response wrapper";
    public static final String SCHEMA_DESC_ID = "Incident ID";
    public static final String SCHEMA_DESC_TITLE = "Incident title";
    public static final String SCHEMA_DESC_SERVICE = "Affected service";
    public static final String SCHEMA_DESC_SERVICE_NAME = "Affected service name";
    public static final String SCHEMA_DESC_SEVERITY = "Severity level";
    public static final String SCHEMA_DESC_STATUS = "Current status";
    public static final String SCHEMA_DESC_OWNER = "Owner";
    public static final String SCHEMA_DESC_OWNER_OPTIONAL = "Owner (optional)";
    public static final String SCHEMA_DESC_SUMMARY = "Summary";
    public static final String SCHEMA_DESC_SUMMARY_OPTIONAL = "Summary (optional)";
    public static final String SCHEMA_DESC_CREATED_AT = "Created at";
    public static final String SCHEMA_DESC_UPDATED_AT = "Updated at";
    public static final String SCHEMA_DESC_CONTENT = "List of items for current page";
    public static final String SCHEMA_DESC_PAGE = "Current page number (0-based)";
    public static final String SCHEMA_DESC_SIZE = "Page size";
    public static final String SCHEMA_DESC_TOTAL_ELEMENTS = "Total number of elements";
    public static final String SCHEMA_DESC_TOTAL_PAGES = "Total number of pages";
    public static final String SCHEMA_DESC_FIRST = "Whether this is the first page";
    public static final String SCHEMA_DESC_LAST = "Whether this is the last page";
    public static final String SCHEMA_EXAMPLE_TITLE = "API latency spike";
    public static final String SCHEMA_EXAMPLE_SERVICE = "payment-service";

    public static final String ERROR_NOT_FOUND = "Not Found";
    public static final String ERROR_VALIDATION_FAILED = "Validation Failed";
    public static final String ERROR_RESPONSE_KEY_TIMESTAMP = "timestamp";
    public static final String ERROR_RESPONSE_KEY_STATUS = "status";
    public static final String ERROR_RESPONSE_KEY_ERROR = "error";
    public static final String ERROR_RESPONSE_KEY_MESSAGE = "message";
    public static final String ERROR_RESPONSE_KEY_ERRORS = "errors";
    public static final String MESSAGE_INCIDENT_NOT_FOUND = "Incident not found with id: ";

    // Security / JWT
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String CLAIM_SUBJECT = "sub";
}
