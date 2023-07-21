package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.User;
import github.mmusica.ppmtool.payload.AuthenticationRequest;
import github.mmusica.ppmtool.services.MapValidationErrorService;
import github.mmusica.ppmtool.services.UserService;
import github.mmusica.ppmtool.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    final MapValidationErrorService mapValidationErrorService;
    final UserService userService;
    final UserValidator userValidator;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequest authenticationRequest, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) return errorMap;
        return ResponseEntity.ok(userService.authenticate(authenticationRequest));

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);
        //validate passwords
        ResponseEntity<?> errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) {
            return errorMap;
        }
        user.setConfirmPassword("");
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
