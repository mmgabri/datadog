package br.com.mmgabri.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;

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
                    "transaction_tye", "CL",
                    "protocol_type", "MQ",
                    "product_name", "COMPRA_NACIONAL_COM_CHIP_SENHA",
                    "operation_name", "DEBITO_COM_SEGURANCA",
                    "status", status,
                    "app", "autorizador");
        } catch (Exception e) {
            logger.error("Erro ao incrementar contador de métrica", e);
            throw new RuntimeException(e);
        }
    }
}
