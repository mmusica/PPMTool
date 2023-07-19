package github.mmusica.ppmtool.web;

import github.mmusica.ppmtool.domain.User;
import github.mmusica.ppmtool.services.MapValidationErrorService;
import github.mmusica.ppmtool.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    final MapValidationErrorService mapValidationErrorService;
    final UserService userService;

    @Autowired
    public UserController(MapValidationErrorService mapValidationErrorService, UserService userService) {
        this.mapValidationErrorService = mapValidationErrorService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        //validate passwords
        ResponseEntity<?> errorMap = mapValidationErrorService.getFieldNameErrorMap(result);
        if (errorMap != null) {
            return errorMap;
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
