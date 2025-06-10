package cl.kartingrm.client_service.dto;

public record ClientDTO(
        @jakarta.validation.constraints.Email String email,
        @jakarta.validation.constraints.NotBlank String name,
        String phone) {}
