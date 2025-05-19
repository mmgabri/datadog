package br.com.mmgabri;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Service
public class ExecuteService {

    public Mono<String> execute(long duration) {
        return Mono.just(gerarReturnCode())
                .delayElement(Duration.ofMillis(duration));
    }

    public String gerarReturnCode() {
        Random random = new Random();
        int chance = random.nextInt(100);

        if (chance < 70) {
            return "00";
        } else {
            int numero = 1 + random.nextInt(14);
            return String.format("%02d", numero);
        }
    }
}
