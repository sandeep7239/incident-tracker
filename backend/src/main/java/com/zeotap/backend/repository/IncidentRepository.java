package com.zeotap.backend.repository;

import com.zeotap.backend.constant.QueryConstants;
import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {

    @Query(QueryConstants.FIND_ALL_FILTERED_JPQL)
    Page<Incident> findAllFiltered(
        @Param(QueryConstants.PARAM_SEARCH) String search,
        @Param(QueryConstants.PARAM_SERVICE) String service,
        @Param(QueryConstants.PARAM_SEVERITY) Severity severity,
        @Param(QueryConstants.PARAM_STATUS) Status status,
        Pageable pageable
    );
}
