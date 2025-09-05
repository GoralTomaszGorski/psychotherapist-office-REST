package pl.goral.psychotherapistofficerest.dto.response;

public record AuthResponseDto(
        String token,
        String email,
        Long userId)
{}
