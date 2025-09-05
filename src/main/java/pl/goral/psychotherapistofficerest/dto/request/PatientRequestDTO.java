package pl.goral.psychotherapistofficerest.dto.request;

import jakarta.validation.constraints.*;

public record PatientRequestDTO(
        @Size(max = 50, message = "Nick must be less than 50 characters")
        String nick,

        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @NotBlank(message = "Surname cannot be blank")
        @Size(max = 100, message = "Surname must be less than 100 characters")
        String surname,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Telephone cannot be blank")
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Telephone must be a valid phone number")
        String telephone,

        @Min(value = 1930, message = "Year of birth must be after 1900")
        @Max(value = 2025, message = "Year of birth cannot be in the future")
        int yearOfBirth,

        @Size(max = 1000, message = "Information must be less than 1000 characters")
        String information,

        boolean approval
) {}