# psychotherapist-office-REST

REST API for managing a psychotherapist's office.  
Provides patient management, therapy schedules, calendar slots, user authentication and authorization.

## Technology Stack

- Java 21
- Spring Boot
- Maven
- Liquibase (database migrations)
- H2 (dev/test) / PostgreSQL (prod)
- JWT (authentication)
- Swagger (API documentation)
- GitHub Actions (CI/CD)

## How to run locally

1. **Configure the dev profile:**
    - The file `src/main/resources/application-dev.properties` is set up for H2 and Liquibase migrations.

2. **Run the application:**
   ```sh
   mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
   ```
   or
   ```sh
   mvn clean package
   java -jar target/psychotherapist-office-REST.jar --spring.profiles.active=dev
   ```

3. **H2 Console:**  
   Available at `/h2-console`.

## How to run tests

```sh
mvn clean test -Dspring.profiles.active=dev
```

## API Documentation

Swagger UI is available at:
`http://localhost:8091/api/swagger-ui/index.html`
`/api/swagger-ui.html`  
OpenAPI: `/api/v1/docs`

## CI/CD

Project uses GitHub Actions workflow (Maven build + tests).

## Author

Tomasz Górski  




API under construction currently I have the following endpoints

<img width="998" height="884" alt="GitHub Actions result" src="https://github.com/user-attachments/assets/adaefab0-97fb-4c87-aa13-3f9652fd06f7" />

<img alt="Endpoints screenshot" src="src/main/resources/screenshots/Zrzut%20ekranu%20z%202025-10-03%2011-44-27.png" />
