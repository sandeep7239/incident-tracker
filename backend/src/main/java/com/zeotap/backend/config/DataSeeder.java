package com.zeotap.backend.config;

import com.zeotap.backend.entity.Incident;
import com.zeotap.backend.entity.Severity;
import com.zeotap.backend.entity.Status;
import com.zeotap.backend.repository.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private final IncidentRepository incidentRepository;

    private static final String[] SERVICES = {
        "payment-service", "auth-service", "user-service", "order-service", "inventory-service",
        "notification-service", "analytics-service", "api-gateway", "search-service", "reporting-service"
    };

    private static final String[] TITLES = {
        "Checkout API latency exceeding 2s P95",
        "Database connection pool exhausted on primary",
        "Stripe webhook delivery failures",
        "OAuth token refresh 5xx errors",
        "Order service CPU at 95% - possible memory leak",
        "SSL certificate expiry in 14 days - api-gateway",
        "Kafka consumer lag > 50k messages",
        "Redis cache stampede during flash sale",
        "Disk usage critical on analytics workers",
        "DNS resolution timeouts for external vendors",
        "Thread pool saturated - payment-service",
        "Deadlock in order inventory transaction",
        "Third-party fraud API timeout rate 30%",
        "SQS queue backlog - notification worker down",
        "Session store (ElastiCache) unreachable",
        "Search index out of sync - product catalog",
        "PII export job failing - GDPR request blocked",
        "Data pipeline backfill stuck at 60%"
    };

    private static final String[] SUMMARIES = {
        "Customers reporting failed payments. Checkout P95 latency spiked from 400ms to 2.1s. Suspect DB connection pool saturation.",
        "Primary DB connection pool hit max size. Replica lag under 1s. Scaling pool and adding read replicas.",
        "Stripe webhook retries failing after 3 attempts. Investigating signature validation and endpoint health.",
        "Identity provider returning 503. Token refresh failing for ~15% of active sessions. Fallback to cached tokens in progress.",
        "Order service heap usage growing 2% per hour. Restart scheduled. Profiling for leak.",
        "Certificate for api-gateway expires 2025-03-01. Renewal and deployment planned.",
        "Consumer group 'order-events' lag at 52k. One consumer instance was restarted. Catching up.",
        "Redis cache miss storm during flash sale. Added circuit breaker and staggered TTL.",
        "Analytics worker /data volume at 98%. Log rotation and archival triggered.",
        "External vendor DNS failing from our region. Using alternate resolver as workaround.",
        "Payment service thread pool at max. Queue depth growing. Scaling pods and tuning pool size.",
        "Deadlock detected in order-inventory update. Code fix deployed; monitoring.",
        "Fraud check API timeout set to 5s; 30% of calls timing out. Contacting vendor.",
        "Notification worker crashed. SQS backlog at 12k. Worker restarted; processing.",
        "ElastiCache cluster in AZ-2 unreachable. Failover to standby in progress.",
        "Product search returning stale results. Full reindex triggered; ETA 2h.",
        "GDPR export job failing on large accounts. Increasing timeout and chunk size.",
        "Backfill job for 2025-01 data stuck. Investigating partition skew."
    };

    private static final String[] OWNERS = {
        "sandeep.singh@zeotap.com", "dixit.raj@zeotap.com", "saurabh.panwar@zeotap.com",
        "ritik.modi@zeotap.com", "anand.kumar@zeotap.com", "gagan.bindal@zeotap.com", null, null
    };

    @Override
    public void run(String... args) {
        if (incidentRepository.count() > 0) {
            log.info("Incidents already present, skipping seed.");
            return;
        }
        Random random = new Random(42);
        for (int i = 0; i < 200; i++) {
            int titleIdx = random.nextInt(TITLES.length);
            int summaryIdx = random.nextInt(SUMMARIES.length);
            Incident incident = Incident.builder()
                .title(TITLES[titleIdx] + " [" + (i + 1) + "]")
                .service(SERVICES[random.nextInt(SERVICES.length)])
                .severity(Severity.values()[random.nextInt(Severity.values().length)])
                .status(Status.values()[random.nextInt(Status.values().length)])
                .owner(OWNERS[random.nextInt(OWNERS.length)])
                .summary(SUMMARIES[summaryIdx])
                .build();
            incidentRepository.save(incident);
        }
        log.info("Seeded 200 incidents.");
    }
}
