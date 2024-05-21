package az.job.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String type;
    private String token;
    private String refreshToken;

}