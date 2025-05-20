package br.com.mmgabri.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

public final class MetricUtils {
    private static final Logger logger = LoggerFactory.getLogger(MetricUtils.class);

    public static void incrementCounter(MetricsService metricsService, String metricName) {

        try {
            metricsService.counter(
                    metricName,
                    "app", "autorizador");
        } catch (Exception e) {
            logger.error("Erro ao incrementar contador de métrica", e);
            throw new RuntimeException(e);
        }
    }


    public static void incrementMetric(MetricsService metricsService, String metricName, String status, OffsetDateTime startTime) {

        try {
            metricsService.summary(
                    metricName,
                    startTime,
                    "transaction_tye", getTag(),
                    "protocol_type", getTag(),
                    "product_name", getTag(),
                    "operation_name", getTag(),
                    "status", status,
                    "app", "autorizador");
        } catch (Exception e) {
            logger.error("Erro ao incrementar contador de métrica", e);
            throw new RuntimeException(e);
        }
    }

    public static String getTag() {
        int randomAscii = ThreadLocalRandom.current().nextInt(65, 91);
        return Character.toString((char) randomAscii);
    }

}
