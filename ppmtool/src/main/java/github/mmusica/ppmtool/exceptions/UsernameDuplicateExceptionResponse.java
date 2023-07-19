package github.mmusica.ppmtool.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameDuplicateExceptionResponse {

    private String username;
}
