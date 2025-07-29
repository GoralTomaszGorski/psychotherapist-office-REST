package pl.goral.psychotherapistofficerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "pl.goral.psychotherapistofficerest.repository")
public class PsychotherapistOfficeRestApplication {


    private static final Logger logger = LoggerFactory.getLogger(PsychotherapistOfficeRestApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(PsychotherapistOfficeRestApplication.class, args);
            logger.info("Aplikacja została uruchomiona pomyślnie");
        } catch (Exception e) {
            logger.error("Błąd podczas uruchamiania aplikacji", e);
            System.exit(1);
        }
    }
}
