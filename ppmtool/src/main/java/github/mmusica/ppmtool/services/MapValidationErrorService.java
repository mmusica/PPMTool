package github.mmusica.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> getFieldNameErrorMap(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMessage = new HashMap<>();
            result.getFieldErrors().
                    forEach(fieldError -> errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
