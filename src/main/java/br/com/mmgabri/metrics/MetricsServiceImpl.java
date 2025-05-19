package br.com.mmgabri.metrics;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private final MeterRegistry meterRegistry;

    @Override
    public void counter(String metricName, String... tags) {
        meterRegistry.counter(metricName, tags).increment();
    }

    @Override
    public void summary(String metricName, OffsetDateTime datetime, String... tags) {
        var duration = 0L;
        duration = Duration.between(datetime, OffsetDateTime.now()).toMillis();
        DistributionSummary summary = meterRegistry.summary(metricName, tags);
        summary.record(duration);
    }
}
