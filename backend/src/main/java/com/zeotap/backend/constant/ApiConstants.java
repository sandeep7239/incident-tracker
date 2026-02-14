package com.zeotap.backend.constant;

/**
 * Constants for REST API paths, request params, and Swagger documentation.
 */
public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE = "/api";
    public static final String API_AUTH = "/api/auth";
    public static final String API_AUTH_LOGIN = "/api/auth/login";
    public static final String API_INCIDENTS = "/api/incidents";
    public static final String PATH_ID = "/{id}";

    public static final String PARAM_ID = "id";

    public static final String PARAM_PAGE = "page";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_SEARCH = "search";
    public static final String PARAM_SERVICE = "service";
    public static final String PARAM_SEVERITY = "severity";
    public static final String PARAM_STATUS = "status";
    public static final String PARAM_SORT_BY = "sortBy";
    public static final String PARAM_SORT_DIR = "sortDir";

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";
    public static final String DEFAULT_SORT_DIR = "desc";

    public static final String SWAGGER_TAG_NAME = "Incidents";
    public static final String SWAGGER_TAG_DESCRIPTION = "Create, list, get, and update incidents";

    public static final String SWAGGER_CREATE_SUMMARY = "Create incident";
    public static final String SWAGGER_CREATE_DESCRIPTION = "Create a new incident with validation";
    public static final String SWAGGER_LIST_SUMMARY = "List incidents";
    public static final String SWAGGER_LIST_DESCRIPTION = "Get paginated, filterable, sortable list of incidents";
    public static final String SWAGGER_GET_BY_ID_SUMMARY = "Get incident by ID";
    public static final String SWAGGER_UPDATE_SUMMARY = "Update incident";
    public static final String SWAGGER_UPDATE_DESCRIPTION = "Partial update of an incident (e.g. status)";

    public static final String SWAGGER_PARAM_PAGE = "Page number (0-based)";
    public static final String SWAGGER_PARAM_SIZE = "Page size";
    public static final String SWAGGER_PARAM_SEARCH = "Search in title, service, owner";
    public static final String SWAGGER_PARAM_SERVICE = "Filter by service name";
    public static final String SWAGGER_PARAM_SEVERITY = "Filter by severity";
    public static final String SWAGGER_PARAM_STATUS = "Filter by status";
    public static final String SWAGGER_PARAM_SORT_BY = "Sort field (e.g. createdAt, title, severity, status)";
    public static final String SWAGGER_PARAM_SORT_DIR = "Sort direction: asc or desc";
    public static final String SWAGGER_PARAM_ID = "Incident UUID";

    // Auth - Swagger
    public static final String SWAGGER_TAG_AUTH = "Authentication";
    public static final String SWAGGER_TAG_AUTH_DESCRIPTION = "Login to obtain JWT token";
    public static final String SWAGGER_LOGIN_SUMMARY = "Login";
    public static final String SWAGGER_LOGIN_DESCRIPTION = "Returns JWT token for Authorization header";
}
