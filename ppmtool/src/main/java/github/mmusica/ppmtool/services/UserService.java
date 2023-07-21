package github.mmusica.ppmtool.services;

import github.mmusica.ppmtool.domain.Role;
import github.mmusica.ppmtool.domain.User;
import github.mmusica.ppmtool.exceptions.UsernameDuplicateException;
import github.mmusica.ppmtool.payload.AuthenticationRequest;
import github.mmusica.ppmtool.payload.AuthenticationResponse;
import github.mmusica.ppmtool.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public User saveUser(User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            newUser.setRole(Role.USER);
            //password and confirm password need to match
            //dont show or persiste confirmPassword
            
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameDuplicateException("Username %s already exists!".formatted(newUser.getUsername()));
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        var userToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(userToken).build();
    }
}
