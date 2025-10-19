# psychotherapist-office-REST

REST API for managing a psychotherapist's office.  
Features: patient management, therapies, calendar slots, user authentication & authorization (JWT), API documentation (Swagger / springdoc).

---

## Technology stack

- Java 21
- Spring Boot
- Gradle (this project uses Gradle via the wrapper `./gradlew`)
- Liquibase (DB migrations)
- H2 (dev/test) / PostgreSQL (prod)
- JWT (authentication)
- springdoc / Swagger UI (API docs)
- JaCoCo (coverage)
- GitHub Actions (CI)

> Note: The project previously mentioned Maven examples — this repository uses Gradle. Use the Gradle wrapper (`./gradlew`) included in the repo.

---

## Requirements

- Java 21 (installed locally or provided by Gradle toolchain)
- Gradle wrapper (`./gradlew`) — always use the wrapper shipped with the project

---

## Run locally (development profile)

1. Ensure JDK 21 is available.
2. Start the application using the `dev` profile:

- run directly with Gradle:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

- or build a jar and run:
```bash
./gradlew clean bootJar
java -jar build/libs/*-SNAPSHOT.jar --spring.profiles.active=dev
```

3. H2 Console (if enabled in `application-dev.properties`):
```
/h2-console
```

---

## Useful Gradle commands

- Clean and build:
```bash
./gradlew clean build
```

- Run tests:
```bash
./gradlew clean test -Dspring.profiles.active=dev
```

- Generate JaCoCo coverage report:
```bash
./gradlew clean test jacocoTestReport
# HTML report: build/reports/jacoco/test/html/index.html
```

- Run the app:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

- Build executable JAR:
```bash
./gradlew clean bootJar
```

- Show runtime dependencies:
```bash
./gradlew dependencies --configuration runtimeClasspath
```

- Dependency insight (find conflicts):
```bash
./gradlew dependencyInsight --dependency spring-web --configuration runtimeClasspath
./gradlew dependencyInsight --dependency spring-webmvc --configuration runtimeClasspath
./gradlew dependencyInsight --dependency org.springdoc:springdoc-openapi-starter-webmvc-ui --configuration runtimeClasspath
```

- Refresh dependencies (force re-download):
```bash
./gradlew clean build --refresh-dependencies
```

- Stop Gradle daemons (sometimes helps when switching JDKs):
```bash
./gradlew --stop
```

---

## Tests & reports

- JUnit test results:
    - XML: `build/test-results/test`
    - HTML: `build/reports/tests/test/index.html`

- JaCoCo coverage:
    - HTML: `build/reports/jacoco/test/html/index.html`

In CI (GitHub Actions) these directories are uploaded as artifacts so you can download test output and coverage reports after a run.

---

## CI / GitHub Actions notes

- The included workflow runs tests using the Gradle wrapper and generates JaCoCo reports. It uses `actions/setup-java@v4` to install JDK 21 on the runner.
- Important: do NOT hardcode a machine-specific `org.gradle.java.home` path in `gradle.properties` (for example `/usr/lib/jvm/...`). That path exists on your machine but not on GitHub runners — remove that property from `gradle.properties`. If necessary, pass `-Dorg.gradle.java.home="$JAVA_HOME"` in the Gradle invocation inside the workflow, but removing the fixed property is best.
- Artifacts uploaded by CI:
    - `build/test-results/test`
    - `build/reports/tests/test`
    - `build/reports/jacoco/test/html`

---

## API documentation

- Swagger UI (local):  
  `http://localhost:8091/api/swagger-ui/index.html`

- OpenAPI JSON:  
  `http://localhost:8091/api/v1/docs`


```bash
git checkout -b feature/my-change
```
3. Make changes, run tests locally:
```bash
./gradlew clean test
```
4. Push branch and open a pull request against `development`.

CI runs tests and uploads test/jacoco reports for each PR.

## Author

Tomasz Górski  



## Quick reference (shortcuts)

- Run app: `./gradlew bootRun --args='--spring.profiles.active=dev'`
- Build JAR: `./gradlew clean bootJar`
- Run tests: `./gradlew clean test`
- Coverage report: `./gradlew jacocoTestReport`
- Dependency inspection: `./gradlew dependencyInsight --dependency <artifact> --configuration runtimeClasspath`

API under construction currently I have the following endpoints

<img width="998" height="884" alt="GitHub Actions result" src="https://github.com/user-attachments/assets/adaefab0-97fb-4c87-aa13-3f9652fd06f7" />

<img alt="Endpoints screenshot" src="src/main/resources/screenshots/Zrzut%20ekranu%20z%202025-10-03%2011-44-27.png" />
