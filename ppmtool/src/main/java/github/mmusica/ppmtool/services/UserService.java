package github.mmusica.ppmtool.services;

import github.mmusica.ppmtool.domain.User;
import github.mmusica.ppmtool.exceptions.UsernameDuplicateException;
import github.mmusica.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User saveUser(User newUser) {
        try {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //password and confirm password need to match
            //dont show or persiste confirmPassword
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new UsernameDuplicateException("Username %s already exists!".formatted(newUser.getUsername()));
        }

    }
}
