package br.com.mmgabri;

import br.com.mmgabri.metrics.MetricsService;
import com.timgroup.statsd.StatsDClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Random;

import static br.com.mmgabri.metrics.MetricUtils.incrementMetric;

@RestController
@RequestMapping("/increment")
@AllArgsConstructor
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final ExecuteService service;
    private final MetricsService metricsService;
    private final StatsDClient statsDClient;

    @PostMapping
    public Mono<ResponseEntity<String>> executeTransaction(@RequestBody DelayRequest request) {
        var startTime = OffsetDateTime.now();

        return service.execute(request.delay)
                .map(returnCode -> {
                    incrementMetricMicrometer(startTime, returnCode);
                    incrementMetricDogstatsd(startTime, returnCode);
                    return ResponseEntity.ok("sucess");
                })
                .doOnSuccess(__ -> logger.info("Request processado!"))
                .onErrorResume(e -> {
                    logger.error("Erro no processamento {}", e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error"));
                });
    }

    private void incrementMetricMicrometer(OffsetDateTime startTime, String returnCode) {
        incrementMetric(metricsService, "app_transacao1_duration", returnCode, startTime);
    }

    private void incrementMetricDogstatsd(OffsetDateTime startTime, String returnCode) {
        try {
            statsDClient.incrementCounter("app_transacao1_duration_dogstatsd", "app:autorizador", "status:" + returnCode);
        } catch (Exception e) {
            logger.error("Error ao enviar métrica via dogstatsd");
            throw new RuntimeException(e);
        }
    }

}
