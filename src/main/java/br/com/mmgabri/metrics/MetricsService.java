package br.com.mmgabri.metrics;

import java.time.OffsetDateTime;

public interface MetricsService {
    void counter(String metricName, String... tags);
    void summary(String metricName, OffsetDateTime datetime, String... tags);
}
