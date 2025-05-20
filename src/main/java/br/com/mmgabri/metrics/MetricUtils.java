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
                    "region", getRegion(),
                    "region", getRegion(),
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
        String options = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123";
        int index = ThreadLocalRandom.current().nextInt(options.length());
        return String.valueOf(options.charAt(index));
    }


    public static String getRegion() {
        String[] regions = {"us-east1", "ap-east1", "eu-east1"};
        int index = ThreadLocalRandom.current().nextInt(regions.length);
        return regions[index];
    }

}
