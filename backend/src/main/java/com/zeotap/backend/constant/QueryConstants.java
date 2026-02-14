package com.zeotap.backend.constant;

/**
 * Constants for repository queries (JPQL param names and query strings).
 */
public final class QueryConstants {

    private QueryConstants() {
    }

    public static final String PARAM_SEARCH = "search";
    public static final String PARAM_SERVICE = "service";
    public static final String PARAM_SEVERITY = "severity";
    public static final String PARAM_STATUS = "status";

    public static final String FIND_ALL_FILTERED_JPQL =
        "SELECT i FROM Incident i WHERE "
        + "(:search IS NULL OR :search = '' OR LOWER(i.title) LIKE LOWER(CONCAT(CONCAT('%', :search), '%')) "
        + "OR LOWER(i.service) LIKE LOWER(CONCAT(CONCAT('%', :search), '%')) "
        + "OR (i.owner IS NOT NULL AND LOWER(i.owner) LIKE LOWER(CONCAT(CONCAT('%', :search), '%')))) "
        + "AND (:service IS NULL OR :service = '' OR i.service = :service) "
        + "AND (:severity IS NULL OR i.severity = :severity) "
        + "AND (:status IS NULL OR i.status = :status)";
}
