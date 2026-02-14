package com.zeotap.backend.entity;

import com.zeotap.backend.constant.CommonConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = CommonConstants.TABLE_INCIDENTS, indexes = {
    @Index(name = CommonConstants.INDEX_INCIDENT_SERVICE, columnList = CommonConstants.COLUMN_SERVICE),
    @Index(name = CommonConstants.INDEX_INCIDENT_SEVERITY, columnList = CommonConstants.COLUMN_SEVERITY),
    @Index(name = CommonConstants.INDEX_INCIDENT_STATUS, columnList = CommonConstants.COLUMN_STATUS),
    @Index(name = CommonConstants.INDEX_INCIDENT_CREATED_AT, columnList = CommonConstants.COLUMN_CREATED_AT)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = CommonConstants.LENGTH_TITLE)
    private String title;

    @Column(nullable = false, length = CommonConstants.LENGTH_SERVICE)
    private String service;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = CommonConstants.LENGTH_SEVERITY)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = CommonConstants.LENGTH_STATUS)
    private Status status;

    @Column(length = CommonConstants.LENGTH_OWNER)
    private String owner;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = CommonConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = CommonConstants.COLUMN_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
