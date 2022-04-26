package ru.nsu.vaulin.ris3;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.vaulin.ris3.repository.NodeRepository;

@Configuration
@AllArgsConstructor
public class InitialTest {
    private NodeRepository repository;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            System.out.println("START RUNNING");
            System.out.println(repository.count());
        };
    }
}
