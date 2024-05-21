package az.job.dto;

import java.util.UUID;

public record CompanyDto(
        UUID id,
        String companyName,
        String email,
        String password,
        String phoneNumber
) {
}
