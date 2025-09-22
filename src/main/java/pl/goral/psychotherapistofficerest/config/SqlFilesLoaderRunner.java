package pl.goral.psychotherapistofficerest.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SqlFilesLoaderRunner implements ApplicationRunner {

    private final DataSource dataSource;

    public SqlFilesLoaderRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> sqlFiles = Arrays.asList(
//                "db/testdata/0001_Patient.sql",
//                "db/testdata/0002_Therapy.sql",
//                "db/testdata/0003_Calender.sql",
//                "db/testdata/0004_Appointment.sql",
//                "db/testdata/0005_Users_and_roles.sql",
//                "db/testdata/0006_Counter.sql"
        );

        try (Connection conn = dataSource.getConnection()) {
            for (String sqlFile : sqlFiles) {
                ClassPathResource resource = new ClassPathResource(sqlFile);
                if (resource.exists()) {
                    String sql;
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                        sql = reader.lines().collect(Collectors.joining("\n"));
                    }
                    for (String statement : sql.split(";")) {
                        String trimmed = statement.trim();
                        if (!trimmed.isEmpty()) {
                            try (Statement stmt = conn.createStatement()) {
                                stmt.execute(trimmed);
                            } catch (Exception e) {
                                System.err.println("Error executing statement from file " + sqlFile + ": " + e.getMessage());
                            }
                        }
                    }
                    System.out.println("Loaded SQL file: " + sqlFile);
                } else {
                    System.err.println("SQL file not found: " + sqlFile);
                }
            }
        }
    }
}