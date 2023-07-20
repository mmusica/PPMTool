package github.mmusica.ppmtool.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JWTLoginSuccessResponse {
    private boolean success;
    private String token;
}
