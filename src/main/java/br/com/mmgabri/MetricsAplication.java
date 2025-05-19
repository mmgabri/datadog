package br.com.mmgabri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.mmgabri", "br.com.mmgabri.config"})
public class MetricsAplication {
    public static void main(String[] args) {
        SpringApplication.run(MetricsAplication.class, args);
    }
}
