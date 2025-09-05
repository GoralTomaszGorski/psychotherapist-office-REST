package pl.goral.psychotherapistofficerest.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
@NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "User's password", example = "password123")
    private String password;
}
