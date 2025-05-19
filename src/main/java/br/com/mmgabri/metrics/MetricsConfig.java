package br.com.mmgabri.metrics;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public StatsDClient statsDClient() {
        return new NonBlockingStatsDClientBuilder()
                .prefix("statsd")
                .hostname("localhost")
                .port(8125)
                .build();
    }
}
